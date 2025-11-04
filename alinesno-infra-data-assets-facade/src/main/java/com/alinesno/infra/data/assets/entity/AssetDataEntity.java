package com.alinesno.infra.data.assets.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据资产
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("asset_data")
public class AssetDataEntity extends InfraBaseEntity {

    @TableField("data_name")
    @ColumnComment("数据名称")
    @ColumnType(length = 200)
    private String dataName; // 数据名称

    @TableField("data_description")
    @ColumnComment("数据描述")
    @ColumnType(length = 256)
    private String dataDescription; // 数据描述

    @TableField("data_type")
    @ColumnComment("数据类型")
    @ColumnType(length = 20)
    private String dataType; // 数据类型

    @TableField("data_source")
    @ColumnComment("数据来源")
    @ColumnType(length = 128)
    private String dataSource; // 数据来源

    @TableField("data_owner")
    @ColumnComment("数据拥有者")
    @ColumnType(length = 64)
    private String dataOwner; // 数据拥有者

    @TableField("data_status")
    @ColumnComment("数据状态")
    @ColumnType(length = 10)
    private String dataStatus; // 数据状态

    @TableField("catalog_id")
    @ColumnComment("目录ID")
    private long catalogId; // 目录ID

    @TableField("data_id")
    @ColumnComment("数据ID")
    @ColumnType(length = 64)
    private String dataId; // 数据ID

    @TableField("data_life_cycle")
    @ColumnComment("数据生命周期")
    @ColumnType(length = 32)
    private String dataLifeCycle; // 数据生命周期

    @TableField("data_security")
    @ColumnComment("数据安全")
    @ColumnType(length = 32)
    private String dataSecurity; // 数据安全

    @TableField("data_privacy")
    @ColumnComment("数据隐私")
    @ColumnType(length = 32)
    private String dataPrivacy; // 数据隐私

    @TableField("data_access")
    @ColumnComment("数据访问")
    @ColumnType(length = 20)
    private String dataAccess; // 数据访问

    @TableField("data_usage")
    @ColumnComment("数据使用")
    @ColumnType(length = 20)
    private String dataUsage; // 数据使用

    @TableField("data_size")
    @ColumnComment("大小")
    @ColumnType
    private long dataSize; // 大小

    @TableField("data_format")
    @ColumnComment("格式")
    @ColumnType(length = 16)
    private String dataFormat; // 格式

    @TableField("data_location")
    @ColumnComment("位置")
    @ColumnType(length = 128)
    private String dataLocation; // 位置

    @TableField("data_schema")
    @ColumnComment("schema")
    @ColumnType(length = 32)
    private String dataSchema; // schema

    @TableField("data_table")
    @ColumnComment("表名")
    @ColumnType(length = 64)
    private String dataTableName; // 表

}
