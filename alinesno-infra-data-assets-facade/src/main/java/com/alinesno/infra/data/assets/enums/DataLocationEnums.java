package com.alinesno.infra.data.assets.enums;

import lombok.Getter;

@Getter
public enum DataLocationEnums {

    // 分布式存储、本地存储、云存储
    DISTRIBUTED_STORAGE("分布式存储", "distributed_storage"),
    LOCAL_STORAGE("本地存储", "local_storage"),
    CLOUD_STORAGE("云存储", "cloud_storage");

    private final String name;
    private final String value;

    DataLocationEnums(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
