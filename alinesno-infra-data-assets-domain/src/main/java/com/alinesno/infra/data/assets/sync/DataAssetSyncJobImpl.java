package com.alinesno.infra.data.assets.sync;

import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import com.alinesno.infra.data.assets.entity.ManifestEntity;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.enums.DataSourceTypeEnum;
import com.alinesno.infra.data.assets.job.IDataAssetSyncJob;
import com.alinesno.infra.data.assets.service.IDataSourceConfigService;
import com.alinesno.infra.data.assets.service.IManifestFieldService;
import com.alinesno.infra.data.assets.service.IManifestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class DataAssetSyncJobImpl implements IDataAssetSyncJob {

    @Resource
    private IDataSourceConfigService dataSourceConfigService;

    @Resource
    private IManifestService manifestService;

    @Resource
    private IManifestFieldService manifestFieldService;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    private static final String LOCK_KEY = "data_asset_sync_lock";

    /**
     * 每小时执行一次资产同步任务
     */
    @Scheduled(fixedDelay = 3600000) // 上一次执行结束后延迟1小时再执行
    public void syncAssets() {
        // 分布式环境使用 Redis 锁确保单节点执行
        Boolean locked = redisTemplate.opsForValue()
                .setIfAbsent(LOCK_KEY, "locked", Duration.ofMinutes(55));

        if (Boolean.FALSE.equals(locked)) {
            log.info("其他节点正在执行数据同步任务，跳过本节点执行。");
            return;
        }

        try {
            log.info("【数据资产同步任务】开始执行...");
            List<DataSourceConfigEntity> configs = dataSourceConfigService.list(); // 可按 orgId 过滤

            for (DataSourceConfigEntity config : configs) {
                syncDataSource(config);
            }

            log.info("【数据资产同步任务】执行完成.");
        } finally {
            redisTemplate.delete(LOCK_KEY); // 释放锁
        }
    }

    /**
     * 同步单个数据源的表结构到资产登记表
     */
    public void syncDataSource(DataSourceConfigEntity config) {
        log.info("开始同步数据源: {}", config.getName());
        try (Connection conn = DriverManager.getConnection(
                config.getUrl(), config.getUsername(), config.getPassword())) {

            DatabaseMetaData metaData = conn.getMetaData();

            // 只取目标数据库（catalog）的表
            String catalog = conn.getCatalog();
            log.debug("当前连接的数据库: {}", catalog);

            ResultSet tables = metaData.getTables(catalog, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                String tableComment = tables.getString("REMARKS");

                ManifestEntity manifest = manifestService.lambdaQuery()
                        .eq(ManifestEntity::getTableName, tableName)
                        .eq(ManifestEntity::getDataSourceConfigId, config.getId())
                        .eq(ManifestEntity::getOrgId, config.getOrgId())
                        .one();

                if (manifest == null) {
                    manifest = new ManifestEntity();
                }

                manifest.setTableName(tableName);
                manifest.setDataSource(config.getName());
                manifest.setOrgId(config.getOrgId()); // 组织隔离

                // 默认为3级，而且已经存在则不做修改
                if(manifest.getConfidentialityLevel() == null){
                    manifest.setConfidentialityLevel("3");
                }

                if(manifest.getDescription() == null || !StringUtils.hasLength(manifest.getDescription())){
                    manifest.setDescription(tableComment);
                }

                manifest.setDataSourceConfigId(config.getId());
                manifest.setLastSyncTime(LocalDateTime.now().toString());

                // 调用补充表元数据信息的方法
                syncTableExtraInfo(conn, conn.getCatalog(), tableName, manifest, config.getType());

                // 保存或更新
                manifestService.saveOrUpdate(manifest);

                // 同步字段
                syncTableFields(metaData, tableName, manifest.getId(), config.getOrgId());
            }

        } catch (Exception e) {
            log.error("同步数据源失败: {}", config.getName(), e);
        }

        config.setLastSyncTime(new Date());
        dataSourceConfigService.updateById(config);

    }

    /**
     * 同步单张表的字段信息
     */
    private void syncTableFields(DatabaseMetaData metaData, String tableName, Long manifestId, Long orgId) throws SQLException {
        ResultSet columns = metaData.getColumns(null, null, tableName, "%");

        List<ManifestFieldEntity> fieldList = new ArrayList<>();

        while (columns.next()) {
            ManifestFieldEntity field = new ManifestFieldEntity();
            field.setManifestId(manifestId);
            field.setFieldName(columns.getString("COLUMN_NAME"));
            field.setFieldType(columns.getString("TYPE_NAME"));
            field.setFiledLength(columns.getInt("COLUMN_SIZE"));
            field.setNumericPrecision(columns.getInt("NUM_PREC_RADIX"));
            field.setNumericScale(columns.getInt("DECIMAL_DIGITS"));
            field.setIsNullable("YES".equalsIgnoreCase(columns.getString("IS_NULLABLE")));
            field.setDefaultValue(columns.getString("COLUMN_DEF"));
            field.setIsAutoIncrement("YES".equalsIgnoreCase(columns.getString("IS_AUTOINCREMENT")));
            field.setFieldComment(columns.getString("REMARKS"));
            field.setOrgId(orgId);
            field.setIsPrimaryKey(isPrimaryKey(metaData, tableName, field.getFieldName()));

            field.setIsPrimaryKey(isPrimaryKey(metaData, tableName, field.getFieldName()));

            fieldList.add(field);
        }

        // 删除旧字段记录，重新插入（防止重复）
        manifestFieldService.lambdaUpdate()
                .eq(ManifestFieldEntity::getManifestId, manifestId)
                .remove();

        manifestFieldService.saveBatch(fieldList);
    }

    /**
     * 判断某字段是否为主键
     */
    private boolean isPrimaryKey(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        ResultSet pkRs = metaData.getPrimaryKeys(null, null, tableName);
        while (pkRs.next()) {
            String pkColumnName = pkRs.getString("COLUMN_NAME");
            if (pkColumnName.equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }

    private void syncTableExtraInfo(Connection conn, String schema, String tableName, ManifestEntity manifest, String dbType) throws SQLException {
        if (DataSourceTypeEnum.MYSQL.getCode().equalsIgnoreCase(dbType)) {
            // MySQL版本
            String sql = """
            SELECT 
                TABLE_ROWS,
                DATA_LENGTH,
                INDEX_LENGTH,
                CREATE_TIME,
                UPDATE_TIME,
                ENGINE,
                TABLE_COLLATION
            FROM information_schema.TABLES
            WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?
            """;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, schema);
                ps.setString(2, tableName);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        manifest.setRowCount(rs.getLong("TABLE_ROWS"));
                        manifest.setDataSizeBytes(rs.getLong("DATA_LENGTH"));
                        manifest.setIndexSizeBytes(rs.getLong("INDEX_LENGTH"));
                        manifest.setTableCreateTime(rs.getTimestamp("CREATE_TIME"));
                        manifest.setTableUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                        manifest.setEngine(rs.getString("ENGINE"));
                        manifest.setCollation(rs.getString("TABLE_COLLATION"));
                    }
                }
            }

        } else if (DataSourceTypeEnum.POSTGRESQL.getCode().equalsIgnoreCase(dbType)) {
            // PostgreSQL版本
            String sql = """
            SELECT 
                reltuples::bigint AS row_count,
                pg_relation_size(oid) AS data_size_bytes,
                (pg_total_relation_size(oid) - pg_relation_size(oid)) AS index_size_bytes,
                pg_total_relation_size(oid) AS total_size_bytes
            FROM pg_class
            WHERE relname = ? AND relkind = 'r'
            """;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, tableName);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        manifest.setRowCount(rs.getLong("row_count"));
                        manifest.setDataSizeBytes(rs.getLong("data_size_bytes"));
                        manifest.setIndexSizeBytes(rs.getLong("index_size_bytes"));
                        // total_size_bytes 不一定要存，如果你想直接存总大小可以用这个
                    }
                }
            }

            // 获取schema名
            try (PreparedStatement ps = conn.prepareStatement("SELECT nspname FROM pg_namespace WHERE oid = (SELECT relnamespace FROM pg_class WHERE relname = ?)")) {
                ps.setString(1, tableName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        manifest.setSchemaName(rs.getString("nspname"));
                    }
                }
            }
        }
    }

}