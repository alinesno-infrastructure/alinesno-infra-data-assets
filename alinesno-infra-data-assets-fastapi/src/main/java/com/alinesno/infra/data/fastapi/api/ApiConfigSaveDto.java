package com.alinesno.infra.data.fastapi.api;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.facade.base.BaseDto;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 新增/保存 DTO（id 可空）
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiConfigSaveDto extends BaseDto {

    // 接口名称不能为空
    @NotBlank(message = "接口名称为空")
    private String name;

    // 接口描述不能为空（修正：String类型应使用@NotBlank而非@NotNull）
    @NotBlank(message = "接口描述为空")
    private String description;

    // 脚本类型必须为groovy或sql，默认值为groovy
    @Pattern(regexp = "groovy|sql", message = "脚本类型必须为 groovy 或 sql")
    private String scriptType = "groovy";

    // 是否开启为布尔值，不能为null（布尔值使用@NotNull校验非空）
    @NotNull(message = "是否开启不能为空")
    private boolean enabled;

    // 数据源ID不能为空
    @NotBlank(message = "数据源ID不能为空")
    private String datasourceId ;

    // 脚本内容不能为空
    @NotBlank(message = "脚本不能为空")
    private String groovyScript;

    // 请求路径不能为空
    @NotBlank(message = "请求路径不能为空")
    private String path;

    // 参数列表不能为空（至少包含一个参数）
    @NotEmpty(message = "参数列表不能为空")
    private List<ApiParamDto> params;

    /**
     * 从实体对象中创建DTO
     * @param entity
     * @return
     */
    public static ApiConfigSaveDto fromEntity(ApiConfigEntity entity) {
        ApiConfigSaveDto dto = new ApiConfigSaveDto();
        BeanUtils.copyProperties(entity, dto);

        dto.setName(entity.getName());
        dto.setDescription(entity.getNote());
        dto.setGroovyScript(entity.getGroovyScript());
        dto.setScriptType(entity.getExecuteType());
        dto.setDatasourceId(entity.getDatasourceId());
        dto.setPath(entity.getPath());
        dto.setParams(JSONObject.parseArray(entity.getJsonParam(), ApiParamDto.class));
        dto.setEnabled(entity.isEnabled());

        return dto;
    }

    /**
     * 转换为实体对象
     * @return
     */
    public static ApiConfigEntity toEntity(ApiConfigSaveDto dto) {
        ApiConfigEntity entity = new ApiConfigEntity();
        BeanUtils.copyProperties(dto, entity);

        entity.setName(dto.getName());
        entity.setNote(dto.getDescription());
        entity.setGroovyScript(dto.getGroovyScript());
        entity.setExecuteType(dto.getScriptType());
        entity.setDatasourceId(dto.getDatasourceId());
        entity.setPath(dto.getPath());
        entity.setJsonParam(JSONObject.toJSONString(dto.getParams()));
        entity.setEnabled(dto.isEnabled());

        return entity;
    }

    /**
     * 接口参数子DTO
     */
    @Data
    @NoArgsConstructor
    public static class ApiParamDto {

        // 参数名称不能为空
        @NotBlank(message = "参数名称不能为空")
        private String name;

        // 参数类型不能为空，且只能是number/string等指定类型
        @NotBlank(message = "参数类型不能为空")
        @Pattern(regexp = "number|string|boolean|object|array", message = "参数类型不合法")
        private String type;

        // 是否必填不能为null
        @NotNull(message = "参数是否必填不能为空")
        private boolean required;

        // 参数描述不能为空
        @NotBlank(message = "参数描述不能为空")
        private String description;
    }
}