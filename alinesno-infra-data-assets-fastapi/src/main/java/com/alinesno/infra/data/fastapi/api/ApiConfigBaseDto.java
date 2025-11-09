package com.alinesno.infra.data.fastapi.api;

import com.alinesno.infra.common.facade.base.BaseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 通用 API 配置 DTO 基类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiConfigBaseDto extends BaseDto {

    @NotBlank(message = "接口名称不能为空")
    private String name;

    // 前端使用 description，实体对应 note，控制器内做映射
    private String description;

    // 可选路径
    private String path;

    // 访问权限（0-private; 1-public）
    private Integer access;

    // 分组ID
    private String groupId;

    // 内容类型
    private String contentType;

    // 任务
    private String task;

    // 执行器相关
    private String datasourceId;

    // 执行类型（如 groovy/sql）
    @Pattern(regexp = "groovy|sql", message = "脚本类型必须为 groovy 或 sql")
    private String scriptType = "groovy";

    private String groovyScript;

    private String runSql;

    private Boolean openTran = false;

    // 入参列表
    @Valid
    private List<ApiParamDto> params;
}