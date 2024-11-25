package com.alinesno.infra.data.fastapi.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import com.alinesno.infra.data.fastapi.service.IApiGroupService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 处理与BusinessLogEntity相关的请求的Controller。
 * 继承自BaseController类并实现IBusinessLogService接口。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/fastapi/apiConfig")
public class ApiConfigController extends BaseController<ApiConfigEntity, IApiConfigService> {

    @Autowired
    private IApiConfigService service;

    @Autowired
    private IApiGroupService groupService;

    /**
     * 获取BusinessLogEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model Model对象。
     * @param page DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @DataPermissionScope
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign(), page);
    }

    @DataPermissionQuery
    @GetMapping("/catalogTreeSelect")
    public AjaxResult catalogTreeSelect(PermissionQuery query){
        return AjaxResult.success("success" , groupService.selectCatalogTreeList(query)) ;
    }

    /**
     * 更新执行sql操作
     * @param entity
     * @param id
     * @return
     */
    @PostMapping("/updateExecuteSql")
    public AjaxResult updateExecuteSql(@RequestBody ApiConfigEntity entity , String id){

        log.debug("id = {}" , id);
        log.debug("entity = {}" , entity);

        UpdateWrapper<ApiConfigEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("datasource_id",  entity.getDatasourceId());
        updateWrapper.set("run_sql", entity.getRunSql());
        updateWrapper.set("open_tran", entity.isOpenTran());
        service.update(null, updateWrapper);

        return AjaxResult.success() ;
    }

    @DataPermissionSave
    @Override
    public AjaxResult save(Model model, @RequestBody ApiConfigEntity entity) throws Exception {
        return super.save(model, entity);
    }

    @Override
    public IApiConfigService getFeign() {
        return this.service;
    }
}