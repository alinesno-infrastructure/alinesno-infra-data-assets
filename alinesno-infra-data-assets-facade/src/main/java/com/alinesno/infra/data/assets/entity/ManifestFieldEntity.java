package com.alinesno.infra.data.assets.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("assets_manifest_field")
@TableComment("数据清单")
public class ManifestFieldEntity extends InfraBaseEntity {

    @Column(length = 50 , comment = "清单ID" , isNull = false)
    @TableField("manifest_id")
    private Long manifestId = 0L;

    @Column(length = 50 , comment = "字段名" , isNull = false)
    @TableField
    private String name;

    @Column(length = 50 , comment = "字段类型" , isNull = false)
    @TableField
    private String type;

    @Column(length = 50 , comment = "字段长度" , isNull = false)
    @TableField
    private Integer length;

    @Column(length = 50 , comment = "是否为空")
    @TableField
    private Boolean isNullable;

    @Column(length = 50 , comment = "是否为主键")
    @TableField
    private Boolean isPrimaryKey;

    // 字段注释，可选
    @TableField
    private String comment;
}
