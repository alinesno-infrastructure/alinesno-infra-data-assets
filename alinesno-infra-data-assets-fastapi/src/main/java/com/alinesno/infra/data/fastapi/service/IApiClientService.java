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
     * 校验客户端token，并将 client 与 orgId 设置到 request attribute 中
     * @return 返回 ApiClientEntity（方便上层使用）
     */
    ApiClientEntity validateClientToken(HttpServletRequest request);

    /**
     * 获取测试用的 token
     * @param orgId
     * @return
     */
    ApiClientEntity getTokenForTest(Long orgId);
}
