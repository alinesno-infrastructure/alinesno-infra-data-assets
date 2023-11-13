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
    private static final long serialVersionUID = 1L;
    // fields
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
    //getter and setter
    public String getSourceSystem(){
        return this.sourceSystem;
    }
    public AssetStatisticsEntity setSourceSystem(String arg){
        this.sourceSystem = arg;
        return this;
    }
    public String getDataAsset(){
        return this.dataAsset;
    }
    public AssetStatisticsEntity setDataAsset(String arg){
        this.dataAsset = arg;
        return this;
    }
    public Long getRunningDays(){
        return this.runningDays;
    }
    public AssetStatisticsEntity setRunningDays(Long arg){
        this.runningDays = arg;
        return this;
    }
    public BigDecimal getDataStorage(){
        return this.dataStorage;
    }
    public AssetStatisticsEntity setDataStorage(BigDecimal arg){
        this.dataStorage = arg;
        return this;
    }
    public Long getDataRequests(){
        return this.dataRequests;
    }
    public AssetStatisticsEntity setDataRequests(Long arg){
        this.dataRequests = arg;
        return this;
    }
    public Long getDataSubjects(){
        return this.dataSubjects;
    }
    public AssetStatisticsEntity setDataSubjects(Long arg){
        this.dataSubjects = arg;
        return this;
    }
    public Long getAssetIndustryCategories(){
        return this.assetIndustryCategories;
    }
    public AssetStatisticsEntity setAssetIndustryCategories(Long arg){
        this.assetIndustryCategories = arg;
        return this;
    }
    public Long getAssetSubjects(){
        return this.assetSubjects;
    }
    public AssetStatisticsEntity setAssetSubjects(Long arg){
        this.assetSubjects = arg;
        return this;
    }
    public Long getAssetBusinessCategories(){
        return this.assetBusinessCategories;
    }
    public AssetStatisticsEntity setAssetBusinessCategories(Long arg){
        this.assetBusinessCategories = arg;
        return this;
    }
    public Long getAssetSpecialTopics(){
        return this.assetSpecialTopics;
    }
    public AssetStatisticsEntity setAssetSpecialTopics(Long arg){
        this.assetSpecialTopics = arg;
        return this;
    }
}
