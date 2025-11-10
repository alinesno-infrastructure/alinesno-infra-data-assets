package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiConfigMapper;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.lang.exception.RpcServiceRuntimeException;

@Slf4j
@Service
public class ApiConfigServiceImpl extends IBaseServiceImpl<ApiConfigEntity, ApiConfigMapper> implements IApiConfigService {

    @Override
    public ApiConfigEntity getByUri(String uri) {
        return this.lambdaQuery().eq(ApiConfigEntity::getPath, uri).one() ;
    }

    @Override
    public ApiConfigEntity getByUri(String path, Long orgId) {
        if (path == null){
            return null;
        }

        // 1. 优先按 orgId 查找（这里示例把 ApiConfigEntity.groupId 等同为 orgId，如你表中是其他字段请替换）
        if (orgId == null) {
            throw new RpcServiceRuntimeException("路径["+path+"]组织编号为空") ;
        }

        LambdaQueryWrapper<ApiConfigEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(ApiConfigEntity::getPath, path)
                .eq(ApiConfigEntity::getOrgId, orgId)
                .eq(ApiConfigEntity::isEnabled, true);

        return this.getOne(qw, false);
    }

}
