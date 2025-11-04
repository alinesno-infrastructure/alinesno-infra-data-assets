package com.alinesno.infra.data.fastapi.service;

import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.fastapi.api.TreeSelectDto;
import com.alinesno.infra.data.fastapi.entity.ApiGroupEntity;

import java.util.List;

public interface IApiGroupService extends IBaseService<ApiGroupEntity> {

    /**
     * 查询出指令类型列表
     *
     * @param promptCatalog
     * @param query
     * @return
     */
    List<ApiGroupEntity> selectCatalogList(ApiGroupEntity promptCatalog, PermissionQuery query);

    /**
     * 保存用户类型
     * @param entity
     */
    void insertCatalog(ApiGroupEntity entity);

    /**
     * 查询类型列表树
     * @return
     */
    List<TreeSelectDto> selectCatalogTreeList(PermissionQuery query);
}
