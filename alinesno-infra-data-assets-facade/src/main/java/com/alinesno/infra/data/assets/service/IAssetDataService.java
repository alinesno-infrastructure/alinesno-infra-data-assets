package com.alinesno.infra.data.assets.service;


import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.assets.api.AssetsTableDataInfo;
import com.alinesno.infra.data.assets.entity.AssetDataEntity;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;

import java.util.List;

/**
 * 资产收藏Service接口
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
public interface IAssetDataService extends IBaseService<AssetDataEntity> {

    /**
     * 查询资产数据
     *
     * @param page
     * @param manifestId
     * @param fieldList
     * @return
     */
    AssetsTableDataInfo findManifestData(DatatablesPageBean page, String manifestId, List<ManifestFieldEntity> fieldList);

}
