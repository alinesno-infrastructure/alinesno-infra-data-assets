package com.alinesno.infra.data.assets.sample.impl;

import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.entity.AssetDataEntity;
import com.alinesno.infra.data.assets.entity.LabelEntity;
import com.alinesno.infra.data.assets.sample.ISimpleService;
import com.alinesno.infra.data.assets.sample.builder.DataAssetGenerator;
import com.alinesno.infra.data.assets.sample.builder.DataAssetsBuilder;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import com.alinesno.infra.data.assets.service.IAssetDataService;
import com.alinesno.infra.data.assets.service.ILabelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SimpleServiceImpl implements ISimpleService {

    @Autowired
    private IAssetCatalogService catalogService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private IAssetDataService dataService ;

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

        if(dataService.count(new LambdaQueryWrapper<>()) == 0){
            List<AssetDataEntity> assetDataList = DataAssetGenerator.generateSampleData();
            dataService.saveBatch(assetDataList) ;
        }

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

    @Override
    public void label() {
        if(labelService.count(new LambdaQueryWrapper<>()) == 0){
            List<LabelEntity> labelList = DataAssetsBuilder.initializeProductionLabels() ;
            labelService.saveBatch(labelList) ;
        }
    }
}
