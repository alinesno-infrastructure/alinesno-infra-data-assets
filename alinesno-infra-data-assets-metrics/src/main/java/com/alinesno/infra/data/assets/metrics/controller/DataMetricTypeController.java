package com.alinesno.infra.data.assets.metrics.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.metrics.entity.DataMetricTypeEntity;
import com.alinesno.infra.data.assets.metrics.service.IDataMetricTypeService;
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
@RequestMapping("/api/infra/data/assets/metricType")
public class DataMetricTypeController extends BaseController<DataMetricTypeEntity, IDataMetricTypeService> {

    @Autowired
    private IDataMetricTypeService assetCatalogService;

    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign() , page) ;
    }

    @GetMapping("/list")
    public AjaxResult list(DataMetricTypeEntity promptCatalog) {
        List<DataMetricTypeEntity> promptCatalogEntities = assetCatalogService.selectCatalogList(promptCatalog);

        return AjaxResult.success("操作成功." , promptCatalogEntities) ;
    }

    /**
     * 保存角色类型
     * @return
     */
    @PostMapping("/insertCatalog")
    public AjaxResult insertCatalog(@RequestBody DataMetricTypeEntity entity){

        assetCatalogService.insertCatalog(entity) ;

        return AjaxResult.success("操作成功.") ;
    }

    /**
     * 获取到子类
     * @param deptId
     * @return
     */
    @GetMapping("/excludeChild/{id}")
    public AjaxResult excludeChild(@PathVariable(value = "id", required = false) Long deptId)
    {
        List<DataMetricTypeEntity> depts = assetCatalogService.selectCatalogList(new DataMetricTypeEntity());
        depts.removeIf(d -> d.getId().longValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return AjaxResult.success("操作成功." , depts);
    }

    @Override
    public IDataMetricTypeService getFeign() {
        return this.assetCatalogService;
    }
}
