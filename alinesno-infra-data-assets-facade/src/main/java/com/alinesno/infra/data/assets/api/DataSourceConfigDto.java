package com.alinesno.infra.data.assets.api;

import com.alinesno.infra.common.facade.base.BaseDto;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * 数据源配置数据传输对象
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
public class DataSourceConfigDto extends BaseDto {

    @NotBlank(message = "数据源名称不能为空")
    private String name;

    @NotBlank(message = "数据源类型不能为空")
    private String type;

    @NotBlank(message = "连接地址不能为空")
    private String url;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String driverClassName;

    private Integer connectionTimeout;

    private Integer maxPoolSize;

    private String remark;

    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 转换为实体对象
     * @return
     */
    public DataSourceConfigEntity toEntity() {
        DataSourceConfigEntity entity = new DataSourceConfigEntity();

        BeanUtils.copyProperties(this, entity);

        entity.setName(this.name);
        entity.setType(this.type);
        entity.setUrl(this.url);
        entity.setUsername(this.username);
        entity.setPassword(this.password);
        entity.setDriverClassName(this.driverClassName);
        entity.setConnectionTimeout(this.connectionTimeout);
        entity.setRemark(this.remark);

        return entity ;
    }
}