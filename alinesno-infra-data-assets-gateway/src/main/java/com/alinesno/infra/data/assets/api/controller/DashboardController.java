package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.login.account.CurrentAccountBean;
import com.alinesno.infra.common.web.adapter.login.annotation.CurrentAccount;
import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.entity.LabelEntity;
import com.alinesno.infra.data.assets.entity.ManifestEntity;
import com.alinesno.infra.data.assets.key.KeyGenerator;
import com.alinesno.infra.data.assets.metrics.entity.DataMetricEntity;
import com.alinesno.infra.data.assets.metrics.service.IDataMetricService;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import com.alinesno.infra.data.assets.service.IAssetTypeService;
import com.alinesno.infra.data.assets.service.ILabelService;
import com.alinesno.infra.data.assets.service.IManifestService;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/assets/dashboard")
public class DashboardController {

    @Autowired
    private IAssetCatalogService assetCatalogService ;

    @Autowired
    private IManifestService manifestService ;

    @Autowired
    private IAssetTypeService assetTypeService ;

    @Autowired
    private IApiConfigService apiConfigService ;

    @Autowired
    private IDataMetricService dataMetricService ;

    @Autowired
    private ILabelService labelService ;

    /**
     * 获取到最新的10条数据资产目录
     * @param query
     * @return
     */
    @DataPermissionQuery
    @RequestMapping("/topCatalog")
    public AjaxResult topCatalog(PermissionQuery query) {
        return AjaxResult.success(assetCatalogService.topCatalog(10 , query)) ;
    }

    /**
     * 获取到资产情况
     */
    @DataPermissionQuery
    @RequestMapping("/getAssetStatus")
    public AjaxResult getAssetStatus(PermissionQuery query) {

        // 资产总量、类型总量、主题总量、调用统计、指标统计
        QueryWrapper<ManifestEntity> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.setEntityClass(ManifestEntity.class) ;
        queryWrapper.eq("org_id" , query.getOrgId()) ;
        queryWrapper.select("SUM(row_count) as totalRowCount");
        Map<String, Object> maniFestResult = manifestService.getMap(queryWrapper) ;
        // 处理为null的异常
        long assetCount = maniFestResult == null || maniFestResult.get("totalRowCount") == null ? 0 : ((Number) maniFestResult.get("totalRowCount")).longValue();

        LambdaQueryWrapper<AssetCatalogEntity> queryWrapper2 = new LambdaQueryWrapper<>() ;
        queryWrapper2.setEntityClass(AssetCatalogEntity.class) ;
        query.toWrapper(queryWrapper2);
        long typeCount = assetCatalogService.count(queryWrapper2) ;

        LambdaQueryWrapper<LabelEntity> queryWrapper3 = new LambdaQueryWrapper<>() ;
        queryWrapper3.setEntityClass(LabelEntity.class) ;
        query.toWrapper(queryWrapper3);
        long topicCount = labelService.count(queryWrapper3) ;

        // 调用次数
        QueryWrapper<ApiConfigEntity> queryWrapper4 = new QueryWrapper<>() ;
        queryWrapper4.setEntityClass(ApiConfigEntity.class) ;
        queryWrapper4.eq("org_id" , query.getOrgId()) ;
        queryWrapper4.select("SUM(use_count) as totalUseCount");
        Map<String, Object> result = apiConfigService.getMap(queryWrapper4) ;
        // 处理为null的异常
        long totalUseCount = result == null || result.get("totalUseCount") == null ? 0 : ((Number) result.get("totalUseCount")).longValue();

        LambdaQueryWrapper<DataMetricEntity> queryWrapper5 = new LambdaQueryWrapper<>() ;
        queryWrapper5.setEntityClass(DataMetricEntity.class) ;
        query.toWrapper(queryWrapper5);
        long metricCount = dataMetricService.count(queryWrapper5) ;

        Map<String, Long> map = new HashMap<>() ;
        map.put("assetCount" , assetCount) ;
        map.put("typeCount" , typeCount) ;
        map.put("topicCount" , topicCount) ;
        map.put("totalUseCount" , totalUseCount) ;
        map.put("metricCount" , metricCount) ;

        return AjaxResult.success("操作成功" , map) ;

    }

    /**
     * 获取到用户密钥
      */
    @RequestMapping("/getApiKey")
    public AjaxResult getApiKey(@CurrentAccount CurrentAccountBean account) {
        log.info("获取到用户密钥") ;
        String apiKey = KeyGenerator.generateKey(String.valueOf(account.getId()) ,String.valueOf(account.getOrgId())) ;
        return AjaxResult.success("操作成功" , apiKey) ;
    }
}
