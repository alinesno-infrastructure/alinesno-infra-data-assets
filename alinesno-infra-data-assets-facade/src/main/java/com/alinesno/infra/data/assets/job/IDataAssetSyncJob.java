package com.alinesno.infra.data.assets.job;

import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;

/**
 * 数据资产同步任务接口
 */
public interface IDataAssetSyncJob {

    /**
     * 同步数据源
     * @param config
     */
    void syncDataSource(DataSourceConfigEntity config) ;

}
