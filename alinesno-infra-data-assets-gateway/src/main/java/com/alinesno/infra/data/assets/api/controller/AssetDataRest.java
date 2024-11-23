package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.api.AssetsTableDataInfo;
import com.alinesno.infra.data.assets.entity.AssetDataEntity;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import com.alinesno.infra.data.assets.service.IAssetDataService;
import com.alinesno.infra.data.assets.service.IManifestFieldService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
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
@RequestMapping("/api/infra/data/assets/assetData")
public class AssetDataRest extends BaseController<AssetDataEntity, IAssetDataService> {

    @Autowired
    private IAssetDataService service;

    @Autowired
    private IAssetCatalogService catalogService ;


    @Autowired
    private IManifestFieldService manifestFieldService ;

    /**
     * 获取BusinessLogEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model Model对象。
     * @param page DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @ResponseBody
    @PostMapping("/datatables")
    public AssetsTableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        String manifestId =  request.getParameter("manifestId") ;

        if(StringUtils.isEmpty(manifestId)){
            return new AssetsTableDataInfo();
        }

        // 从ManifestField中查询出表的字段信息
        LambdaQueryWrapper<ManifestFieldEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ManifestFieldEntity::getManifestId, manifestId);
        List<ManifestFieldEntity> fieldList = manifestFieldService.list(queryWrapper);

        return service.findManifestData(page,manifestId , fieldList);
    }

    @Override
    public AjaxResult save(Model model, @RequestBody AssetDataEntity entity) throws Exception {
        service.save(entity) ;
        return this.ok();
    }

    /**
     *
     * @return
     */
    @PostMapping("/updatePromptContent")
    public AjaxResult updatePromptContent(@RequestBody List<AssetDataEntity> messageDto , String postId){
        service.updateBatchById(messageDto) ;
        return ok() ;
    }

    @DataPermissionQuery
    @GetMapping("/catalogTreeSelect")
    public AjaxResult catalogTreeSelect(PermissionQuery query){
        return AjaxResult.success("success" , catalogService.selectCatalogTreeList(query)) ;
    }

    @GetMapping("/catalogManifestTreeSelect")
    public AjaxResult catalogManifestTreeSelect(PermissionQuery query){
        return AjaxResult.success("success" , catalogService.catalogManifestTreeSelect(query)) ;
    }
    @Override
    public IAssetDataService getFeign() {
        return this.service;
    }
}
