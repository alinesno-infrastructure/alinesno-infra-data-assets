package com.alinesno.infra.data.fastapi.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 入参 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiParamDto {

    @NotBlank(message = "参数名不能为空")
    private String name;

    @NotBlank(message = "参数类型不能为空")
    @Pattern(regexp = "string|number|boolean|object|array", message = "参数类型不合法")
    private String type;

    private Boolean required = false;

    private String description;
}