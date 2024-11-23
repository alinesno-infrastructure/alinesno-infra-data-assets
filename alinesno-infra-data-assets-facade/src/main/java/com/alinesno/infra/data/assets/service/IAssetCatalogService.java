package com.alinesno.infra.data.assets.service;


import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.assets.api.TreeSelectDto;
import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;

import java.util.List;

/**
 * 资产编目Service接口
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
public interface IAssetCatalogService extends IBaseService<AssetCatalogEntity> {

    /**
     * 查询出指令类型列表
     *
     * @param promptCatalog
     * @param query
     * @return
     */
    List<AssetCatalogEntity> selectCatalogList(AssetCatalogEntity promptCatalog, PermissionQuery query);

    /**
     * 保存用户类型
     * @param entity
     */
    void insertCatalog(AssetCatalogEntity entity);

    /**
     * 查询类型列表树
     * @return
     */
    List<TreeSelectDto> selectCatalogTreeList(PermissionQuery query);

    /**
     * 查询顶级类型列表
     * @param count
     * @return
     */
    List<AssetCatalogEntity> topCatalog(int count);

    /**
     * 查询清单列表信息
     * @return
     */
    List<TreeSelectDto> catalogManifestTreeSelect(PermissionQuery query);
}
