package com.alinesno.infra.data.fastapi.api;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 脚本校验/更新 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiScriptRequestDto {

    @NotBlank(message = "脚本内容不能为空")
    private String script;

    @NotBlank(message = "API标识符不能为空")
    private String apiId;

    @NotBlank(message = "脚本类型不能为空")
    private String type;

    // 校验参数（字符串形式），可选
    private String params;
}