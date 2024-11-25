package com.alinesno.infra.data.assets.enums;

import lombok.Getter;

/**
 * 数据安全级别枚举类
 */
@Getter
public enum DataSecurityLevel {

    PUB(1, "Public", "公开数据，无敏感信息，对数据丢失或泄露的影响较小"),
    INT(2, "Internal", "仅限公司内部使用，对数据丢失或泄露有一定影响"),
    CON(3, "Confidential", "重要数据，对数据丢失或泄露有较大影响，需严格保护"),
    HIC(4, "Highly Confidential", "非常重要的数据，对数据丢失或泄露有严重影响，需最高级别保护"),
    TPS(5, "Top Secret", "极其重要的数据，对数据丢失或泄露有灾难性影响，需最严格的保护措施");

    private final int key;
    private final String value;
    private final String desc;

    // 构造函数
    DataSecurityLevel(int key, String value, String desc) {
        this.key = key;
        this.value = value;
        this.desc = desc;
    }

    // 根据级别代码获取枚举
    public static DataSecurityLevel fromKey(int key) {
        for (DataSecurityLevel dataSecurityLevel : DataSecurityLevel.values()) {
            if (dataSecurityLevel.getKey() == key) {
                return dataSecurityLevel;
            }
        }
        throw new IllegalArgumentException("Invalid key: " + key);
    }

    // 根据级别名称获取枚举
    public static DataSecurityLevel fromValue(String value) {
        for (DataSecurityLevel dataSecurityLevel : DataSecurityLevel.values()) {
            if (dataSecurityLevel.getValue().equals(value)) {
                return dataSecurityLevel;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }


}