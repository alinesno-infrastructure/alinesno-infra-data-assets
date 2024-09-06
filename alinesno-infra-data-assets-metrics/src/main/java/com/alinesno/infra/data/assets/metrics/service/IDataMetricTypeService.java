package com.alinesno.infra.data.assets.metrics.service;


import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.assets.metrics.dto.MetricTreeSelectDto;
import com.alinesno.infra.data.assets.metrics.entity.DataMetricTypeEntity;

import java.util.List;

/**
 * 资产编目Service接口
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
public interface IDataMetricTypeService extends IBaseService<DataMetricTypeEntity> {

    /**
     * 查询出指令类型列表
     * @param promptCatalog
     * @return
     */
    List<DataMetricTypeEntity> selectCatalogList(DataMetricTypeEntity promptCatalog);

    /**
     * 保存用户类型
     * @param entity
     */
    void insertCatalog(DataMetricTypeEntity entity);

    /**
     * 查询类型列表树
     * @return
     */
    List<MetricTreeSelectDto> selectCatalogTreeList();

    /**
     * 查询顶级类型列表
     * @param count
     * @return
     */
    List<DataMetricTypeEntity> topCatalog(int count);
}
