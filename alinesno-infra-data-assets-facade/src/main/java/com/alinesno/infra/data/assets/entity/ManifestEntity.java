package com.alinesno.infra.data.assets.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据清单
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("assets_manifest")
@TableComment("数据清单")
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
    @Column(length = 50 , comment = "资产标签" )
    private String assetTag;

}
