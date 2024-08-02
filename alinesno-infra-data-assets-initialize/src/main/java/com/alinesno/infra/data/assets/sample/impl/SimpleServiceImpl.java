package com.alinesno.infra.data.assets.sample.impl;

import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.sample.ISimpleService;
import com.alinesno.infra.data.assets.sample.utils.DataAssetsBuilder;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SimpleServiceImpl implements ISimpleService {

    @Autowired
    private IAssetCatalogService catalogService;

    @Override
    public void catalog() {

        AssetCatalogEntity demoAssetCatalogEntity = catalogService.getById(1L) ;
        if(demoAssetCatalogEntity == null){
            List<AssetCatalogEntity> dataAssets = DataAssetsBuilder.buildDataAssets() ;
            catalogService.saveBatch(dataAssets) ;
        }

    }

    @Override
    public void data() {

    }

    @Override
    public void access() {

    }

    @Override
    public void dataSecurity() {

    }

    @Override
    public void dataLifeCycle() {

    }
}
