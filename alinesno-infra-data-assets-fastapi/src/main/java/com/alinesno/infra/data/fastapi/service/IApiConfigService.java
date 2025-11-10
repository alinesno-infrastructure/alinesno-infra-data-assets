package com.alinesno.infra.data.fastapi.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;

public interface IApiConfigService extends IBaseService<ApiConfigEntity> {

    /**
     * 通过请求的URI获取到地址
     * @param uri
     * @return
     */
    ApiConfigEntity getByUri(String uri);

    /**
     * 根据 uri 和 orgId 获取配置，优先匹配 org 级别的配置；若未命中可回退为全局配置（orgId 为 null 表示全局）
     */
    ApiConfigEntity getByUri(String uri, Long orgId);

}
