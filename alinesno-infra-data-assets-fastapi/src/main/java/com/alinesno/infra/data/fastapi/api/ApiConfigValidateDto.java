package com.alinesno.infra.data.fastapi.api;

import lombok.Data;

/**
 * 验证接口
 */
@Data
public class ApiConfigValidateDto {

    /**
     * 接口ID
     */
    private String apiId ;

    /**
     * 参数
     */
    private String params ;

}
