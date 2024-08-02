package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiConfigMapper;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiConfigServiceImpl extends IBaseServiceImpl<ApiConfigEntity, ApiConfigMapper> implements IApiConfigService {
}
