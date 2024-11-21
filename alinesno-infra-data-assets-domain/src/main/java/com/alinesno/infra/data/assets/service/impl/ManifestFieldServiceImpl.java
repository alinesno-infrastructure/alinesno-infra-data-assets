package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.api.TableFieldRequestDto;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.mapper.ManifestFieldMapper;
import com.alinesno.infra.data.assets.service.IManifestFieldService;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 资产收藏Service业务层处理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Service
public class ManifestFieldServiceImpl extends IBaseServiceImpl<ManifestFieldEntity, ManifestFieldMapper> implements IManifestFieldService {

    @Autowired
    private JdbcTemplate pgJdbcTemplate;

    /**
     * 接收表单结构
     *
     * @param fieldRequests
     * @param mId
     */
    @SneakyThrows
    @Override
    public void saveTableStructure(List<TableFieldRequestDto> fieldRequests, long mId) {
        log.debug("fieldRequests={} , mId={}", fieldRequests, mId);

        DynamicDataSourceContextHolder.push("postgresql");
        log.debug("当前使用数据源:{}", pgJdbcTemplate.getDataSource().getConnection().getClientInfo());

        String tableName = "table_" + mId;

        // 检查表是否存在
        boolean tableExists = checkTableExists(tableName);
        if (!tableExists) {
            // 表不存在，创建表
            createTable(tableName, fieldRequests);
        } else {
            // 表存在，更新表结构
            updateTableStructure(tableName, fieldRequests);
        }
//        DynamicDataSourceContextHolder.poll();
//
//        // --->>>>>>>>>>>>>>>>>>> 切换数据库保存元数据
//        DynamicDataSourceContextHolder.push("mysql");
//        log.debug("当前使用数据源:{}", pgJdbcTemplate.getDataSource().getConnection().getClientInfo());
//
//        // 删除掉之前所有当前的表结构
//        LambdaUpdateWrapper<ManifestFieldEntity> wrapper = new LambdaUpdateWrapper<>();
//        wrapper.eq(ManifestFieldEntity::getManifestId, mId);
//        remove(wrapper);
//
//        // 将 fieldRequests 转换为 List<ManifestFieldEntity>，调用mybatis-plus方法保存到数据库中
//        List<ManifestFieldEntity> manifestFieldEntities = fieldRequests.stream()
//                .map(field -> ManifestFieldEntity.builder()
//                        .manifestId(mId)
//                        .name(field.getName())
//                        .type(field.getType())
//                        .length(field.getLength())
//                        .isNullable(field.getIsNullable())
//                        .isPrimaryKey(field.getIsPrimaryKey())
//                        .comment(field.getComment())
//                        .build())
//                .collect(Collectors.toList());
//
//        saveBatch(manifestFieldEntities);
//        DynamicDataSourceContextHolder.poll();
    }


    private boolean checkTableExists(String tableName) {
        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
        return pgJdbcTemplate.query(sql, new Object[]{tableName}, (rs, rowNum) -> rs.getBoolean(1)).get(0);
    }

    private void createTable(String tableName, List<TableFieldRequestDto> fieldRequests) {
        StringBuilder createTableSql = new StringBuilder("CREATE TABLE ").append(tableName).append(" (");

        for (int i = 0; i < fieldRequests.size(); i++) {
            TableFieldRequestDto field = fieldRequests.get(i);
            createTableSql.append(field.getName()).append(" ");
            createTableSql.append(mapTypeToSql(field.getType(), field.getLength()));
            if (!field.getIsNullable()) {
                createTableSql.append(" NOT NULL");
            }
            if (field.getIsPrimaryKey()) {
                createTableSql.append(" PRIMARY KEY");
            }
            if (i < fieldRequests.size() - 1) {
                createTableSql.append(", ");
            }
        }

        createTableSql.append(");");

        pgJdbcTemplate.execute(createTableSql.toString());

        log.debug("createTableSql = {}", createTableSql.toString());

        log.info("Table {} created successfully", tableName);
    }

    private void updateTableStructure(String tableName, List<TableFieldRequestDto> fieldRequests) {
        // 获取现有表结构
        List<String> existingColumns = getExistingColumns(tableName);

        // 获取新表结构
        List<String> newColumns = fieldRequests.stream().map(TableFieldRequestDto::getName).toList();

        // 计算需要删除的字段
        List<String> columnsToDelete = existingColumns.stream()
                .filter(column -> !newColumns.contains(column))
                .toList();

        // 计算需要添加的字段
        List<TableFieldRequestDto> columnsToAdd = fieldRequests.stream()
                .filter(field -> !existingColumns.contains(field.getName()))
                .toList();

        // 计算需要更新的字段
        List<TableFieldRequestDto> columnsToUpdate = fieldRequests.stream()
                .filter(field -> existingColumns.contains(field.getName()))
                .filter(field -> requiresUpdate(tableName, field))
                .toList();

        // 删除字段
        for (String column : columnsToDelete) {
            String dropColumnSql = "ALTER TABLE " + tableName + " DROP COLUMN " + column;
            log.debug("dropColumnSql = {}", dropColumnSql);
            pgJdbcTemplate.execute(dropColumnSql);
            log.info("Column {} dropped from table {}", column, tableName);
        }

        // 添加字段
        for (TableFieldRequestDto field : columnsToAdd) {
            String addColumnSql = "ALTER TABLE " + tableName + " ADD COLUMN " + field.getName() + " " +
                    mapTypeToSql(field.getType(), field.getLength());
            if (!field.getIsNullable()) {
                addColumnSql += " NOT NULL";
            }
            if (field.getIsPrimaryKey()) {
                addColumnSql += " PRIMARY KEY";
            }

            log.debug("addColumnSql = {}", addColumnSql);

            pgJdbcTemplate.execute(addColumnSql);
            log.info("Column {} added to table {}", field.getName(), tableName);
        }

        // 更新字段
        for (TableFieldRequestDto field : columnsToUpdate) {
            String alterColumnSql = "ALTER TABLE " + tableName + " ALTER COLUMN " + field.getName() + " TYPE " +
                    mapTypeToSql(field.getType(), field.getLength());
            if (!field.getIsNullable()) {
                alterColumnSql += ", ALTER COLUMN " + field.getName() + " SET NOT NULL";
            } else {
                alterColumnSql += ", ALTER COLUMN " + field.getName() + " DROP NOT NULL";
            }
            if (field.getIsPrimaryKey()) {
                // 如果是主键，则需要先删除旧的主键约束再添加新的
                String dropPkConstraintSql = "ALTER TABLE " + tableName + " DROP CONSTRAINT IF EXISTS " + tableName + "_pkey";
                pgJdbcTemplate.execute(dropPkConstraintSql);
                alterColumnSql += ", ADD PRIMARY KEY (" + field.getName() + ")";
            }

            log.debug("alterColumnSql = {}", alterColumnSql);

            pgJdbcTemplate.execute(alterColumnSql);
            log.info("Column {} updated in table {}", field.getName(), tableName);
        }
    }

    private boolean requiresUpdate(String tableName, TableFieldRequestDto field) {
        // 这里应该查询数据库获取当前字段的信息，然后与传入的字段信息进行比较
        // 如果有不同，则返回 true 表示需要更新
        // 实现细节取决于你的具体需求和数据库结构
        String columnName = field.getName();
        String currentType = getCurrentColumnType(tableName, columnName);
        Boolean isNullable = isColumnNullable(tableName, columnName);
        Boolean isPrimaryKey = isColumnPrimaryKey(tableName, columnName);

        return !currentType.equals(mapTypeToSql(field.getType(), field.getLength())) ||
                (field.getIsNullable() != null && field.getIsNullable() != isNullable) ||
                (field.getIsPrimaryKey() != null && field.getIsPrimaryKey() != isPrimaryKey);
    }

    private String getCurrentColumnType(String tableName, String columnName) {
        // 查询当前列的数据类型
        String sql = "SELECT udt_name, character_maximum_length FROM information_schema.columns WHERE table_name = ? AND column_name = ?";
        Map<String, Object> columnInfo = pgJdbcTemplate.queryForMap(sql, tableName, columnName);
        String udtName = (String) columnInfo.get("udt_name");
        Integer maxLength = (Integer) columnInfo.get("character_maximum_length");

        return mapTypeToSql(udtName, maxLength);
    }

    private Boolean isColumnNullable(String tableName, String columnName) {
        // 查询当前列是否允许为空
        String sql = "SELECT is_nullable FROM information_schema.columns WHERE table_name = ? AND column_name = ?";
        return "YES".equals(pgJdbcTemplate.queryForObject(sql, new Object[]{tableName, columnName}, String.class));
    }

    private Boolean isColumnPrimaryKey(String tableName, String columnName) {
        // 查询当前列是否为主键
        // 注意：这里假设每个表只有一个主键，如果有复合主键则需要更复杂的逻辑
        String sql = "SELECT a.attname FROM pg_index i JOIN pg_attribute a ON a.attnum = ANY(i.indkey) AND a.attrelid = i.indrelid WHERE i.indisprimary AND a.attname = ? AND i.indrelid::regclass::text = ?";
        return pgJdbcTemplate.queryForObject(sql, new Object[]{columnName, tableName}, Boolean.class);
    }

    private List<String> getExistingColumns(String tableName) {
        String sql = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
        return pgJdbcTemplate.queryForList(sql, new Object[]{tableName}, String.class);
    }


    private String mapTypeToSql(String type, Integer length) {
        return switch (type.toLowerCase()) {
            case "varchar" -> "VARCHAR(" + (length == null ? 255 : length) + ")";
            case "integer" -> "INTEGER";
            case "float" -> "FLOAT";
            case "boolean" -> "BOOLEAN";
            case "date" -> "DATE";
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
    }
}
