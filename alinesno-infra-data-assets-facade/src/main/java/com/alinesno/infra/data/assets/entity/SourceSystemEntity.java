package com.alinesno.infra.data.assets.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能名： 来源系统
 * 数据表：  source_system
 * 表备注： 来源系统表
 * @author luoxiaodong
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("source_system")
@TableComment("来源系统表")
public class SourceSystemEntity extends InfraBaseEntity {
    // fields
    /**
    * 来源系统
    */
    @ColumnComment("来源系统")
    @Excel(name="来源系统")
    @ColumnType(length = 255)
    @TableField("source_system")
    private String sourceSystem;

    /**
    * 系统标识
    */
    @ColumnComment("系统标识")
    @Excel(name="系统标识")
    @ColumnType(length = 11)
    @TableField("system_id")
    private Long systemId;
}
