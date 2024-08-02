package com.alinesno.infra.data.assets.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能名： 资产标签
 * 数据表：  asset_catalog
 * 表备注： 资产标签
 * @author luoxiaodong
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("asset_label")
public class LabelEntity extends InfraBaseEntity {

    @Excel(name="标签名称")
    @TableField("label_name")
    @ColumnType(length=50)
    @ColumnComment("标签名称")
    private String labelName;

    @Excel(name="标签标识")
    @TableField("label_key")
    @ColumnType(length=50)
    @ColumnComment("标签标识")
    private String labelKey ;

    @Excel(name="标签值")
    @TableField("label_value")
    @ColumnType(length=50)
    @ColumnComment("标签值")
    private String labelValue;

    @Excel(name="标签类型")
    @TableField("label_type")
    @ColumnType(length=50)
    @ColumnComment("标签类型")
    private String labelType;

}
