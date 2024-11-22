package com.alinesno.infra.data.assets.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 表字段请求DTO类
 * 用于处理与表字段相关的请求，确保字段信息的准确性
 */
@NoArgsConstructor
@Data
public class TableFieldRequestDto {

    // 列名，不能为空
    @NotBlank(message = "列名不能为空")
    private String name;

    // 数据类型，不能为空
    @NotBlank(message = "数据类型不能为空")
    private String type;

    // 类型长度，必须大于0
    @Positive(message = "类型长度必须大于0")
    private Integer length;

    // 是否为空，不能为空
    @NotNull(message = "是否为空不能为空")
    private Boolean isNullable;

    // 是否为主键，不能为空
    @NotNull(message = "是否为主键不能为空")
    private Boolean isPrimaryKey;

    // 字段注释，可选
    private String comment;

    public TableFieldRequestDto(String filedName, String fieldType, Integer filedLength, Boolean isNullable, Boolean isPrimaryKey, String filedComment) {
        this.name = filedName;
        this.type = fieldType;
        this.length = filedLength;
        this.isNullable = isNullable;
        this.isPrimaryKey = isPrimaryKey;
        this.comment = filedComment;
    }
}
