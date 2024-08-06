package com.alinesno.infra.data.assets.enums;

import lombok.Getter;

@Getter
public enum DataTypeEnums {

    // 视频、图片、文档、音频
    VIDEO("视频", "video"),
    IMAGE("图片", "image"),
    DOCUMENT("文档", "document"),
    AUDIO("音频", "audio"),

    // 模型、数据库、数据库表、数据库字段
    MODEL("模型", "model"),
    DATABASE("数据库", "database"),
    TABLE("数据库表", "table"),
    FIELD("数据库字段", "field"),

    // 语料、文本、文本块
    TEXT("文本", "text"),
    BLOCK("文本块", "block") ;

    private final String name ;
    private final String value ;

    DataTypeEnums(String name, String value) {
        this.name = name ;
        this.value = value ;
    }

}
