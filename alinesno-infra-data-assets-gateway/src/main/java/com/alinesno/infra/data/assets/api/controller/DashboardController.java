package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/assets/dashboard")
public class DashboardController {

    @Autowired
    private IAssetCatalogService assetCatalogService ;

    // 获取到最新的10条数据资产目录
    @RequestMapping("/topCatalog")
    public AjaxResult topCatalog() {
        return AjaxResult.success(assetCatalogService.topCatalog(10)) ;
    }
}
