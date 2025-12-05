package com.alinesno.infra.data.fastapi.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.dto.FieldDto;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.fastapi.api.*;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import com.alinesno.infra.data.fastapi.service.IApiGroupService;
import com.alinesno.infra.data.fastapi.utils.ValidateApiUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private ValidateApiUtils validateApiUtils;

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
     * 保存接口配置（使用 DTO + 校验）
     * @param model
     * @param entity
     * @return
     * @throws Exception
     */
    @DataPermissionSave
    @PostMapping("saveConfigInfo")
    public AjaxResult save(Model model, @RequestBody ApiConfigEntity entity) throws Exception {
        return super.save(model, entity);
    }

    /**
     * 验证接口 validateApiScript
     */
    @PostMapping("/validateApiScript")
    public Object validateApiScript(@RequestBody ApiConfigValidateDto dto) throws IOException {
        return validateApiUtils.validateApiScript(dto) ;
    }


    /**
     * 新增或保存接口配置（id 为空新增，否则走更新）
     */
    @DataPermissionSave
    @PostMapping("/saveApiConfig")
    public AjaxResult saveApiConfig(@Valid @RequestBody ApiConfigSaveDto dto) {

        ApiConfigEntity entity ;

        if(dto.getId() != null){
            Long id = dto.getId() ;
            entity = service.getById(id) ;
            ApiConfigSaveDto.mergeEntity(entity, dto);
            service.updateById(entity);
        }else{
            entity = ApiConfigSaveDto.toEntity(dto) ;
            service.save(entity);
        }

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

    /**
     * 修改启停状态
     * @param field 字段对象
     * @return
     */
    @ResponseBody
    @PostMapping("changeEnableField")
    public AjaxResult changeFiled(@RequestBody FieldDto field) {
        log.debug("field = {}", field);
        Assert.isTrue(field != null && field.getId() != null, "实体对象为空");
        Assert.hasLength(field.getField(), "字段为空");

        LambdaUpdateWrapper<ApiConfigEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ApiConfigEntity::isEnabled, field.getValue().equals("1"));
        updateWrapper.eq(ApiConfigEntity::getId, field.getId());

        boolean b = this.getFeign().update(updateWrapper);

        return b ? ok() : error();
    }

    @Override
    public IApiConfigService getFeign() {
        return this.service;
    }
}