package com.alinesno.infra.data.assets.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("assets_manifest_field")
@TableComment("数据资产字段表")
public class ManifestFieldEntity extends InfraBaseEntity {

    @TableField("manifest_id")
    @Column(comment = "资产登记表ID")
    private Long manifestId;

    @TableField("field_name")
    @Column(length = 200 , comment = "字段名")
    private String fieldName;

    @TableField("field_type")
    @Column(length = 100 , comment = "字段类型")
    private String fieldType;

    @TableField("filed_length")
    @Column(comment = "字段长度")
    private Integer filedLength;

    @TableField("numeric_precision")
    @Column(comment = "数值精度")
    private Integer numericPrecision;

    @TableField("numeric_scale")
    @Column(comment = "小数位")
    private Integer numericScale;

    @TableField("is_nullable")
    @Column(comment = "是否可为空")
    private Boolean isNullable;

    @TableField("is_primary_key")
    @Column(comment = "是否主键")
    private Boolean isPrimaryKey;

    @TableField("is_auto_increment")
    @Column(comment = "是否自增")
    private Boolean isAutoIncrement;

    @TableField("default_value")
    @Column(length = 500 , comment = "默认值")
    private String defaultValue;

    @TableField("field_comment")
    @Column(length = 500 , comment = "字段备注")
    private String fieldComment;

    @TableField("character_set_name")
    @Column(length = 200 , comment = "字符集")
    private String characterSetName;

    @TableField("collation_name")
    @Column(length = 200 , comment = "排序规则")
    private String collationName;

    @TableField("org_id")
    @Column(comment = "组织ID")
    private Long orgId;
}
