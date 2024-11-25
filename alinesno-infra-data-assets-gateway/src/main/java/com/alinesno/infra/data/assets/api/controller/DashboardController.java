package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.login.account.CurrentAccountBean;
import com.alinesno.infra.common.web.adapter.login.annotation.CurrentAccount;
import com.alinesno.infra.data.assets.key.KeyGenerator;
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
     * 获取到用户密钥
      */
    @RequestMapping("/getApiKey")
    public AjaxResult getApiKey(@CurrentAccount CurrentAccountBean account) {
        log.info("获取到用户密钥") ;
        String apiKey = KeyGenerator.generateKey(String.valueOf(account.getId()) ,String.valueOf(account.getOrgId())) ;
        return AjaxResult.success("操作成功" , apiKey) ;
    }
}
