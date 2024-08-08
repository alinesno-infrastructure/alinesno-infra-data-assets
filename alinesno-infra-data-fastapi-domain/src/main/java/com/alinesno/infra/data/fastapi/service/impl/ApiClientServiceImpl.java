package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.fastapi.entity.ApiClientEntity;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.alinesno.infra.data.fastapi.mapper.ApiClientMapper;

@Slf4j
@Service
public class ApiClientServiceImpl extends IBaseServiceImpl<ApiClientEntity, ApiClientMapper> implements IApiClientService {
}
