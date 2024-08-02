package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.fastapi.entity.IpRuleEntity;
import com.alinesno.infra.data.fastapi.mapper.IpRuleMapper;
import com.alinesno.infra.data.fastapi.service.IIpRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IpRuleServiceImpl extends IBaseServiceImpl<IpRuleEntity, IpRuleMapper> implements IIpRuleService {
}
