package com.alinesno.infra.data.assets.service.impl;


import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.entity.AssetTypeEntity;
import com.alinesno.infra.data.assets.mapper.AssetTypeMapper;
import com.alinesno.infra.data.assets.service.IAssetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用于存储资产模型信息Service业务层处理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Service
public class AssetTypeServiceImpl extends IBaseServiceImpl<AssetTypeEntity, AssetTypeMapper> implements IAssetTypeService {
}
