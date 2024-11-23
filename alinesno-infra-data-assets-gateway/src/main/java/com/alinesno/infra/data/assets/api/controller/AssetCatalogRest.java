package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/assets/catalog")
public class AssetCatalogRest extends BaseController<AssetCatalogEntity, IAssetCatalogService> {

    @Autowired
    private IAssetCatalogService assetCatalogService;

    @DataPermissionScope
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign() , page) ;
    }

    @DataPermissionQuery
    @GetMapping("/list")
    public AjaxResult list(AssetCatalogEntity promptCatalog , PermissionQuery query) {
        List<AssetCatalogEntity> promptCatalogEntities = assetCatalogService.selectCatalogList(promptCatalog , query);

        return AjaxResult.success("操作成功." , promptCatalogEntities) ;
    }

    /**
     * 保存角色类型
     * @return
     */
    @DataPermissionSave
    @PostMapping("/insertCatalog")
    public AjaxResult insertCatalog(@RequestBody AssetCatalogEntity entity){

        assetCatalogService.insertCatalog(entity) ;

        return AjaxResult.success("操作成功.") ;
    }

    /**
     * 获取到子类
     * @param deptId
     * @return
     */
    @DataPermissionQuery
    @GetMapping("/excludeChild/{id}")
    public AjaxResult excludeChild(@PathVariable(value = "id", required = false) Long deptId , PermissionQuery query)
    {
        List<AssetCatalogEntity> depts = assetCatalogService.selectCatalogList(new AssetCatalogEntity(), query);
        depts.removeIf(d -> d.getId().longValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return AjaxResult.success("操作成功." , depts);
    }

    @Override
    public IAssetCatalogService getFeign() {
        return this.assetCatalogService;
    }
}
