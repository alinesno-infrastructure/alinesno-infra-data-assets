package com.alinesno.infra.data.assets.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能名： 用于存储资产模型信息
 * 数据表：  asset_type
 * 表备注： 用于存储资产模型信息
 * @author luoxiaodong
 * @since 1.0.0
 */
@TableName("asset_type")
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetTypeEntity extends InfraBaseEntity {
    // fields
    /**
    * 资产类型
    */
    @ColumnComment("资产类型")
    @Excel(name="资产类型")
    @ColumnType(length = 255)
    @TableField("model_name")
    private String modelName;
    /**
    * 描述
    */
    @ColumnComment("描述")
    @Excel(name="描述")
    @ColumnType(length = 255)
    @TableField("industry_category")
    private String industryCategory;
    /**
    * 资产层级
    */
    @ColumnComment("资产层级")
    @Excel(name="资产层级")
    @ColumnType(length = 255)
    @TableField("business_category")
    private String businessCategory;
    /**
    * 存储类型
    */
    @ColumnComment("存储类型")
    @Excel(name="存储类型")
    @ColumnType(length = 255)
    @TableField("resource_name")
    private String resourceName;
    /**
    * 模型数
    */
    @ColumnComment("模型数")
    @Excel(name="模型数")
    @ColumnType(length = 11)
    @TableField("resourcesummary")
    private Long resourcesummary;
    /**
    * 标签
    */
    @ColumnComment("标签")
    @Excel(name="标签")
    @ColumnType(length = 255)
    @TableField("model_tags")
    private String modelTags;
    /**
    * 描述
    */
    @ColumnComment("描述")
    @Excel(name="描述")
    @ColumnType(length = 255)
    @TableField("db_desc")
    private String dbDesc;
    /**
    * 数据库名称
    */
    @ColumnComment("数据库名称")
    @Excel(name="数据库名称")
    @ColumnType(length = 255)
    @TableField("db_dame")
    private String dbName;
    /**
    * 数据库连接
    */
    @ColumnComment("数据库连接")
    @Excel(name="数据库连接")
    @ColumnType(length = 255)
    @TableField("db_url")
    private String dbUrl;
    /**
    * 数据库连接地址
    */
    @ColumnComment("数据库连接地址")
    @Excel(name="数据库连接地址")
    @ColumnType(length = 255)
    @TableField("jdbc_url")
    private String jdbcUrl;
    /**
    * 数据库用户名
    */
    @ColumnComment("数据库用户名")
    @Excel(name="数据库用户名")
    @ColumnType(length = 255)
    @TableField("db_username")
    private String dbUsername;
    /**
    * 数据库密码
    */
    @ColumnComment("数据库密码")
    @Excel(name="数据库密码")
    @ColumnType(length = 255)
    @TableField("db_passwd")
    private String dbPasswd;
    /**
    * 数据库连接端口
    */
    @ColumnComment("数据库连接端口")
    @Excel(name="数据库连接端口")
    @ColumnType(length = 255)
    @TableField("db_port")
    private String dbPort;
    /**
    * 数据库类型
    */
    @ColumnComment("数据库类型")
    @Excel(name="数据库类型")
    @ColumnType(length = 255)
    @TableField("db_type")
    private String dbType;
    /**
    * 作者名
    */
    @ColumnComment("作者名")
    @Excel(name="作者名")
    @ColumnType(length = 255)
    @TableField("author")
    private String author;
    /**
    * 生成包路径
    */
    @ColumnComment("生成包路径")
    @Excel(name="生成包路径")
    @ColumnType(length = 255)
    @TableField("package_path")
    private String packagePath;
    /**
    * 模块名
    */
    @ColumnComment("模块名")
    @Excel(name="模块名")
    @ColumnType(length = 255)
    @TableField("module_name")
    private String moduleName;
    /**
    * 生成类型
    */
    @ColumnComment("生成类型")
    @Excel(name="生成类型")
    @ColumnType(length = 255)
    @TableField("create_type")
    private String createType;
}
