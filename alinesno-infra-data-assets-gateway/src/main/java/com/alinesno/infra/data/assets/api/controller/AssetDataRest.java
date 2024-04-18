package com.alinesno.infra.data.assets.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.common.facade.pageable.ConditionDto;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.entity.AssetCollectionEntity;
import com.alinesno.infra.data.assets.entity.AssetDataEntity;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import com.alinesno.infra.data.assets.service.IAssetCollectionService;
import com.alinesno.infra.data.assets.service.IAssetDataService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
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
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));

        List<ConditionDto> condition =  page.getConditionList() ;

        String catalogId =  request.getParameter("catalogId") ;

        if(StringUtils.isNotBlank(catalogId)){
            ConditionDto dto = new ConditionDto() ;
            dto.setColumn("prompt_type");
            dto.setValue(catalogId);

            condition.add(dto) ;
            page.setConditionList(condition);
        }

        return this.toPage(model, this.getFeign(), page);
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

    @GetMapping("/catalogTreeSelect")
    public AjaxResult catalogTreeSelect(){
        return AjaxResult.success("success" , catalogService.selectCatalogTreeList()) ;
    }

    @Override
    public IAssetDataService getFeign() {
        return this.service;
    }
}
