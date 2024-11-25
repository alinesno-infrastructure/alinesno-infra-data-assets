package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.api.TableFieldRequestDto;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.mapper.ManifestFieldMapper;
import com.alinesno.infra.data.assets.service.IManifestFieldService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.lang.exception.RpcServiceRuntimeException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @DS("postgresql")
    @SneakyThrows
    @Override
    public void saveTableStructure(List<TableFieldRequestDto> fieldRequests, long mId) {
        log.debug("fieldRequests={} , mId={}", fieldRequests, mId);

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

    }

    @Override
    public void saveManifest(List<TableFieldRequestDto> fieldRequests, long mId) {

        // 删除掉之前所有当前的表结构
        LambdaUpdateWrapper<ManifestFieldEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ManifestFieldEntity::getManifestId, mId);
        remove(wrapper);

        // 将 fieldRequests 转换为 List<ManifestFieldEntity>，调用mybatis-plus方法保存到数据库中
        List<ManifestFieldEntity> manifestFieldEntities = fieldRequests.stream()
                .map(field -> {
                    ManifestFieldEntity entity = new ManifestFieldEntity();
                    entity.setManifestId(mId);
                    entity.setFieldName(field.getName());
                    entity.setFieldType(field.getType());
                    entity.setFiledLength(field.getLength());
                    entity.setIsNullable(field.getIsNullable());
                    entity.setIsPrimaryKey(field.getIsPrimaryKey());
                    entity.setFieldComment(field.getComment());
                    return entity;
                })
                .collect(Collectors.toList());

        saveBatch(manifestFieldEntities);
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

        // 插入示例数据
        insertExampleData(tableName, fieldRequests);

    }

    private void insertExampleData(String tableName, List<TableFieldRequestDto> fieldRequests) {
        StringBuilder insertSql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");

        // 构建列名部分
        for (int i = 0; i < fieldRequests.size(); i++) {
            TableFieldRequestDto field = fieldRequests.get(i);
            insertSql.append(field.getName());
            if (i < fieldRequests.size() - 1) {
                insertSql.append(", ");
            }
        }

        insertSql.append(") VALUES (");

        // 构建值部分
        for (int i = 0; i < fieldRequests.size(); i++) {
            TableFieldRequestDto field = fieldRequests.get(i);
            String value = getExampleValue(field.getType());
            insertSql.append(value);
            if (i < fieldRequests.size() - 1) {
                insertSql.append(", ");
            }
        }

        insertSql.append(");");

        pgJdbcTemplate.execute(insertSql.toString());

        log.debug("insertSql = {}", insertSql.toString());

        log.info("Example data inserted into table {}", tableName);
    }

    private String getExampleValue(String type) {
        return switch (type.toLowerCase()) {
            case "varchar", "string" -> "'1'";
            case "integer" -> "123";
            case "float" -> "123.45";
            case "boolean" -> "true";
            case "date" -> "'2024-01-01'";
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
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
            if (columnExists(tableName, column)) {
                String dropColumnSql = "ALTER TABLE " + tableName + " DROP COLUMN " + column;
                log.debug("Executing SQL: {}", dropColumnSql);
                try {
                    pgJdbcTemplate.execute(dropColumnSql);
                    log.info("Column {} dropped from table {}", column, tableName);
                } catch (Exception e) {
                    log.error("Failed to drop column {}: {}", column, e.getMessage());
                    throw new RpcServiceRuntimeException("Failed to drop column " + column, e.getMessage());
                }
            } else {
                log.warn("Column {} does not exist in table {}", column, tableName);
            }
        }

        // 添加字段
        for (TableFieldRequestDto field : columnsToAdd) {
            String addColumnSql = "ALTER TABLE " + tableName + " ADD COLUMN " + field.getName() + " " +
                    mapTypeToSql(field.getType(), field.getLength());

            log.debug("Executing SQL: {}", addColumnSql);
            try {
                pgJdbcTemplate.execute(addColumnSql);
                log.info("Column {} added to table {}", field.getName(), tableName);

                if (!field.getIsNullable()) {
                    // 更新所有行以填充默认值
                    String updateSql = "UPDATE " + tableName + " SET " + field.getName() + " = " + getDefaultValue(field.getType());
                    log.debug("Executing SQL: {}", updateSql);
                    pgJdbcTemplate.execute(updateSql);

                    // 设置 NOT NULL 约束
                    String setNotNullSql = "ALTER TABLE " + tableName + " ALTER COLUMN " + field.getName() + " SET NOT NULL";
                    log.debug("Executing SQL: {}", setNotNullSql);
                    pgJdbcTemplate.execute(setNotNullSql);
                    log.info("Column {} set to NOT NULL in table {}", field.getName(), tableName);
                }
            } catch (Exception e) {
                log.error("Failed to add column {}: {}", field.getName(), e.getMessage());
                throw new RpcServiceRuntimeException("Failed to add column " + field.getName(), e.getMessage());
            }
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
                log.debug("Executing SQL: {}", dropPkConstraintSql);
                try {
                    pgJdbcTemplate.execute(dropPkConstraintSql);
                } catch (Exception e) {
                    log.error("Failed to drop primary key constraint: {}", e.getMessage());
                }
                alterColumnSql += ", ADD PRIMARY KEY (" + field.getName() + ")";
            }

            log.debug("Executing SQL: {}", alterColumnSql);
            try {
                pgJdbcTemplate.execute(alterColumnSql);
                log.info("Column {} updated in table {}", field.getName(), tableName);
            } catch (Exception e) {
                log.error("Failed to update column {}: {}", field.getName(), e.getMessage());
                throw new RpcServiceRuntimeException("Failed to update column " + field.getName(), e.getMessage());
            }
        }
    }

    private String getDefaultValue(String type) {
        return switch (type.toLowerCase()) {
            case "varchar", "string" -> "'0'";
            case "integer" -> "0";
            case "float" -> "0.0";
            case "boolean" -> "false";
            case "date" -> "CURRENT_DATE";
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
    }


    private boolean columnExists(String tableName, String columnName) {
        String sql = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND column_name = ?";
        List<String> columns = pgJdbcTemplate.queryForList(sql, new Object[]{tableName, columnName}, String.class);
        return !columns.isEmpty();
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
        return false;

        // TODO 后期判断主键
//        String sql = "SELECT a.attname FROM pg_index i JOIN pg_attribute a ON a.attnum = ANY(i.indkey) AND a.attrelid = i.indrelid WHERE i.indisprimary AND a.attname = ? AND i.indrelid::regclass::text = ?";
//        return pgJdbcTemplate.queryForObject(sql, new Object[]{columnName, tableName}, Boolean.class);
    }

    private List<String> getExistingColumns(String tableName) {
        String sql = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
        return pgJdbcTemplate.queryForList(sql, new Object[]{tableName}, String.class);
    }


    private String mapTypeToSql(String type, Integer length) {
        return switch (type.toLowerCase()) {
            case "varchar", "string" -> "VARCHAR(" + (length == null ? 255 : length) + ")";
            case "integer" -> "INTEGER";
            case "float" -> "FLOAT";
            case "boolean" -> "BOOLEAN";
            case "date" -> "DATE";
            case "timestamp" -> "TIMESTAMP";
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
    }
}
