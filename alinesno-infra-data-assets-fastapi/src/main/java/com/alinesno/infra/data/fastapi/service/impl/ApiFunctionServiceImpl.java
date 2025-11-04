package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.fastapi.entity.ApiFunctionEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiFunctionMapper;
import com.alinesno.infra.data.fastapi.service.IApiFunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiFunctionServiceImpl extends IBaseServiceImpl<ApiFunctionEntity, ApiFunctionMapper> implements IApiFunctionService {
}
