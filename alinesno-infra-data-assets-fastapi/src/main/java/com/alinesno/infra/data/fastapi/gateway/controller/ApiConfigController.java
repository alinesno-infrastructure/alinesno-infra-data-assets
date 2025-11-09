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
import com.alinesno.infra.data.fastapi.api.*;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import com.alinesno.infra.data.fastapi.service.IApiGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * API配置 Controller，使用 DTO + 校验
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/fastapi/apiConfig")
@Validated
public class ApiConfigController extends BaseController<ApiConfigEntity, IApiConfigService> {

    @Autowired
    private IApiConfigService service;

    @Autowired
    private IApiGroupService groupService;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
     * 获取 API
     */
    @GetMapping("/getApi")
    public AjaxResult getApi(@RequestParam("id") String id){
        ApiConfigEntity entity = service.getById(id);
        if (entity == null) {
            return AjaxResult.error("接口不存在");
        }

        ApiConfigSaveDto dto = ApiConfigSaveDto.fromEntity(entity);

        return AjaxResult.success("success" , entity) ;
    }

    /**
     * 更新运行脚本（使用 DTO + 校验）
     */
    @PostMapping("/updateApiScript")
    public AjaxResult updateApiScript(@Valid @RequestBody ApiScriptRequestDto dto) {
        ApiConfigEntity entity = service.getById(dto.getApiId());
        if (entity == null) {
            return AjaxResult.error("接口不存在");
        }

        service.updateById(entity);
        return AjaxResult.success("脚本更新成功");
    }

    /**
     * 更新接口配置（使用 DTO + 校验）
     */
    @PostMapping("/updateApiConfig")
    public AjaxResult updateApiConfig(@Valid @RequestBody ApiConfigUpdateDto dto) {
        ApiConfigEntity old = service.getById(dto.getId());
        if (old == null){
            return AjaxResult.error("接口不存在");
        }

        List<String> messages = validateBusiness(dto);
        if (!messages.isEmpty()) {
            return AjaxResult.error(String.join("；", messages));
        }

        service.updateById(old);
        return AjaxResult.success("配置更新成功");
    }

    /**
     * 新增或保存接口配置（id 为空新增，否则走更新）
     */
    @DataPermissionSave
    @PostMapping("/saveApiConfig")
    public AjaxResult saveApiConfig(Model model, @Valid @RequestBody ApiConfigSaveDto dto) {

//        List<String> messages = validateBusiness(dto);
//        if (!messages.isEmpty()) {
//            return AjaxResult.error(String.join("；", messages));
//        }
//
//        if (dto.getId() != null) {
//            ApiConfigEntity entity = new ApiConfigEntity();
//            applyDtoToEntity(dto, entity);
//            service.save(entity);
//            return AjaxResult.success("新增成功");
//        } else {
//            return updateApiConfig(toUpdateDto(dto));
//        }

        ApiConfigEntity entity = dto.toEntity() ;
        service.saveOrUpdate(entity);

        return AjaxResult.success("操作成功." , entity.getId()) ;
    }

    /**
     * 业务层校验（除 @Valid 以外的交叉校验）
     */
    private List<String> validateBusiness(ApiConfigBaseDto dto) {
        List<String> errors = new ArrayList<>();

        // 脚本类型对应脚本内容
        if ("groovy".equalsIgnoreCase(dto.getScriptType())) {
            if (StringUtils.isBlank(dto.getGroovyScript())) {
                errors.add("Groovy脚本不能为空");
            }
        } else if ("sql".equalsIgnoreCase(dto.getScriptType())) {
            if (StringUtils.isBlank(dto.getRunSql())) {
                errors.add("SQL脚本不能为空");
            }
        } else {
            errors.add("脚本类型必须为 groovy 或 sql");
        }

        // 参数重名校验
        if (dto.getParams() != null && !dto.getParams().isEmpty()) {
            Map<String, Long> dup = dto.getParams().stream()
                    .filter(p -> StringUtils.isNotBlank(p.getName()))
                    .collect(Collectors.groupingBy(ApiParamDto::getName, Collectors.counting()));
            List<String> duplicates = dup.entrySet().stream()
                    .filter(e -> e.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            if (!duplicates.isEmpty()) {
                errors.add("入参存在重名: " + String.join(",", duplicates));
            }
        }

        return errors;
    }

    @Override
    public IApiConfigService getFeign() {
        return this.service;
    }
}