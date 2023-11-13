package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.entity.AssetStatisticsEntity;
import com.alinesno.infra.data.assets.mapper.AssetStatisticsMapper;
import com.alinesno.infra.data.assets.service.IAssetStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 数据资产统计Service业务层处理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Service
public class AssetStatisticsServiceImpl extends IBaseServiceImpl<AssetStatisticsEntity, AssetStatisticsMapper> implements IAssetStatisticsService {

}
