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
 * 功能名： 资产编目
 * 数据表：  asset_catalog
 * 表备注： 资产编目表
 * @author luoxiaodong
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("asset_catalog")
public class AssetCatalogEntity extends InfraBaseEntity {
    // fields
    /**
    * 资源名称
    */
    @ColumnComment("资源名称")
    @Excel(name="资源名称")
    @ColumnType(length = 255)
    @TableField("resource_name")
    private String resourceName;
    /**
    * 行业分类
    */
    @ColumnComment("行业分类")
    @Excel(name="行业分类")
    @ColumnType(length = 50)
    @TableField("industry_category")
    private String industryCategory;
    /**
    * 业务分类
    */
    @ColumnComment("业务分类")
    @Excel(name="业务分类")
    @ColumnType(length = 50)
    @TableField("business_category")
    private String businessCategory;
    /**
    * 管理对象
    */
    @ColumnComment("管理对象")
    @Excel(name="管理对象")
    @ColumnType(length = 50)
    @TableField("management_object")
    private String managementObject;
    /**
    * 信息类别
    */
    @ColumnComment("信息类别")
    @Excel(name="信息类别")
    @ColumnType(length = 50)
    @TableField("information_type")
    private String informationType;
    /**
    * 主题分类
    */
    @ColumnComment("主题分类")
    @Excel(name="主题分类")
    @ColumnType(length = 50)
    @TableField("subject_category")
    private String subjectCategory;
    /**
    * 资源提供方
    */
    @ColumnComment("资源提供方")
    @Excel(name="资源提供方")
    @ColumnType(length = 100)
    @TableField("resource_provider")
    private String resourceProvider;
    /**
    * 来源系统
    */
    @ColumnComment("来源系统")
    @Excel(name="来源系统")
    @ColumnType(length = 100)
    @TableField("source_system")
    private String sourceSystem;
    /**
    * 来源数据表
    */
    @ColumnComment("来源数据表")
    @Excel(name="来源数据表")
    @ColumnType(length = 100)
    @TableField("source_data_table")
    private String sourceDataTable;

    /**
    * 状态
    */
    @ColumnComment("状态")
    @Excel(name="状态")
    @ColumnType(length = 20)
    @TableField("status")
    private String status;

    @Excel(name="类型图标")
    @TableField("icon")
    @ColumnType(length=64)
    @ColumnComment("类型图标")
    private String icon;

    @Excel(name="类型名称")
    @TableField("name")
    @ColumnType(length=50)
    @ColumnComment("类型名称")
    private String name ;

    @Excel(name="显示排序")
    @TableField("order_num")
    @ColumnType(length=5 , value = MySqlTypeConstant.INT)
    @ColumnComment("显示排序")
    private int orderNum ;

    @Excel(name="祖级列表")
    @TableField("ancestors")
    @ColumnType(length=256)
    @ColumnComment("祖级列表")
    private String ancestors;

    @Excel(name="父类ID")
    @TableField("parent_id")
    @ColumnType(length=32)
    @ColumnComment("父类ID")
    private Long parentId;

    /** 子类型 */
    @TableField(exist = false)
    private List<AssetCatalogEntity> children = new ArrayList<>();
}
