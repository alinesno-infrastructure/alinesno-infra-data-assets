package com.alinesno.infra.data.assets.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 功能名： 数据资产统计
 * 数据表：  asset_statistics
 * 表备注： 数据资产统计表
 * @author luoxiaodong
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("asset_statistics")
public class AssetStatisticsEntity extends InfraBaseEntity {
    // fields
    //getter and setter
    /**
    * 来源系统
    */
    @ColumnComment("来源系统")
    @Excel(name="来源系统")
    @ColumnType(length = 50)
    @TableField("source_system")
    private String sourceSystem;
    /**
    * 数据资产
    */
    @ColumnComment("数据资产")
    @Excel(name="数据资产")
    @ColumnType(length = 100)
    @TableField("data_asset")
    private String dataAsset;
    /**
    * 运行天数
    */
    @ColumnComment("运行天数")
    @Excel(name="运行天数")
    @ColumnType(length = 11)
    @TableField("running_days")
    private Long runningDays;
    /**
    * 数据存储量
    */
    @ColumnComment("数据存储量")
    @Excel(name="数据存储量")
    @ColumnType(length = 14 , decimalLength = 2)
    @TableField("data_storage")
    private BigDecimal dataStorage;
    /**
    * 数据请求量
    */
    @ColumnComment("数据请求量")
    @Excel(name="数据请求量")
    @ColumnType(length = 11)
    @TableField("data_requests")
    private Long dataRequests;
    /**
    * 数据主题数
    */
    @ColumnComment("数据主题数")
    @Excel(name="数据主题数")
    @ColumnType(length = 11)
    @TableField("data_subjects")
    private Long dataSubjects;
    /**
    * 资产行业分类数
    */
    @ColumnComment("资产行业分类数")
    @Excel(name="资产行业分类数")
    @ColumnType(length = 11)
    @TableField("asset_industry_categories")
    private Long assetIndustryCategories;
    /**
    * 资产主题数
    */
    @ColumnComment("资产主题数")
    @Excel(name="资产主题数")
    @ColumnType(length = 11)
    @TableField("asset_subjects")
    private Long assetSubjects;
    /**
    * 资产业务分类数
    */
    @ColumnComment("资产业务分类数")
    @Excel(name="资产业务分类数")
    @ColumnType(length = 11)
    @TableField("asset_business_categories")
    private Long assetBusinessCategories;
    /**
    * 资产专题数
    */
    @ColumnComment("资产专题数")
    @Excel(name="资产专题数")
    @ColumnType(length = 11)
    @TableField("asset_special_topics")
    private Long assetSpecialTopics;

}
