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
@TableComment("数据清单")
public class ManifestFieldEntity extends InfraBaseEntity {

    @Column(length = 32 , comment = "清单ID" , isNull = false)
    @TableField("manifest_id")
    private Long manifestId = 0L;

    @Column(length = 50 , comment = "字段名" , isNull = false)
    @TableField
    private String fieldName;

    @Column(length = 16 , comment = "字段类型" , isNull = false)
    @TableField
    private String fieldType;

    @Column(length = 11 , comment = "字段长度" , isNull = false)
    @TableField
    private Integer filedLength;

    @Column(length = 50 , comment = "是否为空")
    @TableField
    private Boolean isNullable;

    @Column(length = 50 , comment = "是否为主键")
    @TableField
    private Boolean isPrimaryKey;

    // 字段注释，可选
    @TableField
    @Column(length = 200 , comment = "字段注释")
    private String fieldComment;
}
