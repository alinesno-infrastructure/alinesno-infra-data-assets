package com.alinesno.infra.data.assets.service;


import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.assets.api.TableFieldRequestDto;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;

import java.util.List;

/**
 * 资产清单管理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
public interface IManifestFieldService extends IBaseService<ManifestFieldEntity> {

    /**
     * 保存表结构信息
     *
     * @param fieldRequests
     * @param mId
     */
    void saveTableStructure(List<TableFieldRequestDto> fieldRequests, long mId);

    /**
     * 保存字段信息
     * @param fieldRequests
     * @param mId
     */
    void saveManifest(List<TableFieldRequestDto> fieldRequests, long mId);
}
