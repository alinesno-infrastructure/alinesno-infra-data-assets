package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.mapper.AssetCatalogMapper;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 资产编目Service业务层处理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Service
public class AssetCatalogServiceImpl extends IBaseServiceImpl<AssetCatalogEntity, AssetCatalogMapper> implements IAssetCatalogService {

}
