package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.fastapi.entity.ApiHistoryEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiHistoryMapper;
import com.alinesno.infra.data.fastapi.service.IApiHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiHistoryServiceImpl extends IBaseServiceImpl<ApiHistoryEntity, ApiHistoryMapper> implements IApiHistoryService {
}
