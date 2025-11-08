package com.alinesno.infra.data.assets.service;

import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;

public interface IDataSourceConfigService extends IBaseService<DataSourceConfigEntity> {

    /**
     * 获取当前数据源配置
     * @param permissionQuery
     * @return
     */
    DataSourceConfigEntity getCurrentDatasourceConfig(PermissionQuery permissionQuery);

    /**
     * 获取当前数据源配置
     * @param orgId
     * @return
     */
    DataSourceConfigEntity getCurrentDatasourceConfig(Long orgId);

}