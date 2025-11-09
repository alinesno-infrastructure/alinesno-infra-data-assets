package com.alinesno.infra.data.assets.api;

import com.alinesno.infra.common.facade.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 数据资产登记表，会定时从数据源中同步资产清单列表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ManifestDto extends BaseDto {

    private String tableName;

    private String description;

    private Long dataDomain = 0L;

    private String project;

    private String confidentialityLevel;

    private String assetTag;

    private String lastSyncTime;

    private String dataSource;

    private Long dataSourceConfigId;

    /**
     * 数据源配置
     */
    private DataSourceConfigQueryDto dataSourceConfig;

    private String catalogName;

    private String schemaName;

    private Long rowCount;

    private Long dataSizeBytes;

    private Long indexSizeBytes;

    private String engine;

    private String collation;

    private Date tableCreateTime;

    private Date tableUpdateTime;

}
