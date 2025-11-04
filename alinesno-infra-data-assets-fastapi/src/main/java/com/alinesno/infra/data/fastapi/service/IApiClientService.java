package com.alinesno.infra.data.fastapi.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.fastapi.entity.ApiClientEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface IApiClientService extends IBaseService<ApiClientEntity> {

    /**
     * 保存客户端token
     * @param client
     */
    void saveClientToken(@Valid ApiClientEntity client);

    /**
     * 校验客户端token
     * @param request
     */
    void validateClientToken(HttpServletRequest request);
}
