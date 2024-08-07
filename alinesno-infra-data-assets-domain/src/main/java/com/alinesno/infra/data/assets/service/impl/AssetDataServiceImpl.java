package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.entity.AssetCollectionEntity;
import com.alinesno.infra.data.assets.entity.AssetDataEntity;
import com.alinesno.infra.data.assets.mapper.AssetCollectionMapper;
import com.alinesno.infra.data.assets.mapper.AssetDataMapper;
import com.alinesno.infra.data.assets.service.IAssetCollectionService;
import com.alinesno.infra.data.assets.service.IAssetDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 资产收藏Service业务层处理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Service
public class AssetDataServiceImpl extends IBaseServiceImpl<AssetDataEntity, AssetDataMapper> implements IAssetDataService {
}
