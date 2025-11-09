package com.alinesno.infra.data.assets.api;

import com.alinesno.infra.common.facade.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 数据源配置数据传输对象
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
public class DataSourceConfigQueryDto extends BaseDto {

    private String name;

    private String type;

    private String url;

    private String username;

    private String password;

    private Integer connectionTimeout;

    private String remark;

}