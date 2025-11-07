package com.alinesno.infra.data.assets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源类型枚举
 */
@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {

    MYSQL("mysql", "MySQL"),
    POSTGRESQL("postgresql", "PostgresSQL"),
    CLICKHOUSE("clickhouse", "ClickHouse");

    /**
     * 类型编码（存储到数据库）
     */
    private final String code;

    /**
     * 显示标签（用于前端展示）
     */
    private final String label;

    /**
     * 获取所有枚举的列表（code-label键值对）
     * @return List<Map<String, String>> 包含所有数据源类型的列表
     */
    public static List<Map<String, String>> getList() {
        List<Map<String, String>> list = new ArrayList<>();
        for (DataSourceTypeEnum type : values()) {
            Map<String, String> map = new HashMap<>(2);
            map.put("code", type.getCode());
            map.put("label", type.getLabel());
            list.add(map);
        }
        return list;
    }

    /**
     * 根据code获取枚举
     * @param code 类型编码
     * @return 对应的枚举，不存在则返回null
     */
    public static DataSourceTypeEnum getByCode(String code) {
        for (DataSourceTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 校验code是否有效
     * @param code 类型编码
     * @return 有效返回true，否则false
     */
    public static boolean isValidCode(String code) {
        return getByCode(code) != null;
    }
}