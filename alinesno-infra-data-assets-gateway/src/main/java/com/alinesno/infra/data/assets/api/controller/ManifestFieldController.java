package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.api.TableFieldRequestDto;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.service.IManifestFieldService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产清单控制器。
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/assets/manifestField")
public class ManifestFieldController extends BaseController<ManifestFieldEntity, IManifestFieldService> {

    @Autowired
    private IManifestFieldService service;

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

        return this.toPage(model, this.getFeign(), page);
    }

    @PostMapping("/updateManifestFieldByMId")
    public AjaxResult updateManifestFieldByMId(@Valid @RequestBody List<TableFieldRequestDto> fieldRequests , @RequestParam long mId) {
        service.saveTableStructure(fieldRequests , mId);
        return AjaxResult.success("表结构保存成功");
    }

    @GetMapping("/getManifestFieldByMId")
    public AjaxResult getManifestFieldByMId(@RequestParam long mId) {
        log.debug("getManifestFieldByMId = {}", mId);
        return AjaxResult.success();
    }

    @Override
    public IManifestFieldService getFeign() {
        return this.service;
    }
}