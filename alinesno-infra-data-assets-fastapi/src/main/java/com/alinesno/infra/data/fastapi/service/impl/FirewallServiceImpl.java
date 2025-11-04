package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.fastapi.entity.FirewallEntity;
import com.alinesno.infra.data.fastapi.mapper.FirewallMapper;
import com.alinesno.infra.data.fastapi.service.IFirewallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirewallServiceImpl extends IBaseServiceImpl<FirewallEntity, FirewallMapper> implements IFirewallService {
}
