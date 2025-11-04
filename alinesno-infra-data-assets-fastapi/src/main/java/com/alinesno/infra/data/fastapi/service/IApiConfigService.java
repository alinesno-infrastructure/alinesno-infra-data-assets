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

}
