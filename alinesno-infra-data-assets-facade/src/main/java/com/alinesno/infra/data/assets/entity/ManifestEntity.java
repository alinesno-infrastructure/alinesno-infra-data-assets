package com.alinesno.infra.data.assets.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 数据资产登记表，会定时从数据源中同步资产清单列表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("assets_manifest")
@TableComment("数据资产登记表")
public class ManifestEntity extends InfraBaseEntity {

    @TableField("table_name")
    @Column(length = 50 , comment = "表名" , isNull = false )
    private String tableName;

    @TableField("description")
    @Column(length = 200 , comment = "描述")
    private String description;

    @TableField("data_domain")
    @Column(length = 100 , comment = "数据域")
    private Long dataDomain = 0L;

    @TableField("project")
    @Column(length = 200 , comment = "项目")
    private String project;

    @TableField("confidentiality_level")
    @Column(length = 50 , comment = "保密性等级" )
    private String confidentialityLevel;

    @TableField("asset_tag")
    @Column(length = 512 , comment = "资产标签" )
    private String assetTag;

    // 上次同步时间
    @TableField("last_sync_time")
    @Column(length = 50 , comment = "上次同步时间" )
    private String lastSyncTime;

    // 数据源dataSource
    @TableField("data_source")
    @Column(length = 256, comment = "数据源" )
    private String dataSource;

    // 数据源配置ID
    @TableField("data_source_config_id")
    @Column(comment = "数据源配置ID" )
    private Long dataSourceConfigId;

    @TableField("catalog_name")
    @Column(length = 200 , comment = "数据库目录(Catalog)")
    private String catalogName;

    @TableField("schema_name")
    @Column(length = 200 , comment = "数据库模式(Schema)")
    private String schemaName;

    @TableField("row_count")
    @Column(comment = "表行数")
    private Long rowCount;

    @TableField("data_size_bytes")
    @Column(comment = "数据大小(字节)")
    private Long dataSizeBytes;

    @TableField("index_size_bytes")
    @Column(comment = "索引大小(字节)")
    private Long indexSizeBytes;

    @TableField("engine")
    @Column(length = 100 , comment = "存储引擎")
    private String engine;

    @TableField("collation")
    @Column(length = 100 , comment = "排序规则")
    private String collation;

    @TableField("table_create_time")
    @Column(comment = "表创建时间")
    private Date tableCreateTime;

    @TableField("table_update_time")
    @Column(comment = "表更新时间")
    private Date tableUpdateTime;
}
