package com.alinesno.infra.data.assets.api.utils;

/**
 * JDBC 驱动工具类
 */
public class JdbcDriverUtils {

    /**
     * 根据数据源类型返回对应 JDBC driver 类名
     *
     * @param type 数据源类型 (mysql/postgresql/clickhouse)
     * @return JDBC 驱动类名
     * @throws IllegalArgumentException 不支持的类型时抛出异常
     */
    public static String getDriverByType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("数据库类型不能为空");
        }

        return switch (type.toLowerCase()) {
            case "mysql" -> "com.mysql.cj.jdbc.Driver";
            case "postgresql" -> "org.postgresql.Driver";
            case "clickhouse" -> "ru.yandex.clickhouse.ClickHouseDriver";
            default -> throw new IllegalArgumentException("不支持的数据库类型: " + type);
        };
    }
}