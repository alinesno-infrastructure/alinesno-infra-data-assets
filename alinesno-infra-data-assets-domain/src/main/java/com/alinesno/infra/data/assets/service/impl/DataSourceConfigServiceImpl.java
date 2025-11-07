package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import com.alinesno.infra.data.assets.mapper.DataSourceConfigMapper;
import com.alinesno.infra.data.assets.service.IDataSourceConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 数据源配置服务实现类
 */
@Slf4j
@Service
public class DataSourceConfigServiceImpl extends IBaseServiceImpl<DataSourceConfigEntity, DataSourceConfigMapper> implements IDataSourceConfigService {

    @Override
    public DataSourceConfigEntity getCurrentDatasourceConfig(PermissionQuery permissionQuery) {
        LambdaQueryWrapper<DataSourceConfigEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataSourceConfigEntity::getOrgId, permissionQuery.getOrgId());
        return this.getOne(queryWrapper);
    }

}