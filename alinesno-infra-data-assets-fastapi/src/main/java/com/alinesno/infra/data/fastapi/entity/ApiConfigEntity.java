package com.alinesno.infra.data.fastapi.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * API接口配置
 */
@EqualsAndHashCode(callSuper = true)
@TableName("api_config")
@Data
@TableComment("API接口配置表")
public class ApiConfigEntity extends InfraBaseEntity {

    /**
     * 名称
     */
    @TableField("name")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 255)
    @ColumnComment("名称")
    private String name;

    /**
     * 备注
     */
    @TableField("note")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 255)
    @ColumnComment("备注")
    private String note;

    /**
     * 路径
     */
    @TableField("path")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 255)
    @ColumnComment("路径")
    private String path;

    /**
     * 访问权限
     */
    @TableField("access")
    @ColumnType(value = MySqlTypeConstant.INT, length = 11)
    @ColumnComment("访问权限 0-private;1-public")
    private Integer access;

    /**
     * 分组ID
     */
    @TableField("group_id")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 20)
    @ColumnComment("服务目录")
    private String groupId;

    /**
     * 是否启用
     */
    @TableField("enabled")
    @ColumnType(value = MySqlTypeConstant.BIT, length = 1)
    private boolean enabled;

    /**
     * 内容类型
     */
    @TableField("content_type")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 50)
    @ColumnComment("内容类型")
    private String contentType;

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 执行器_start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @TableField("datasource_id")
    @ColumnType(value = MySqlTypeConstant.VARCHAR)
    @ColumnComment("数据库源ID")
    private String datasourceId ;

    @TableField("execute_type")
    @ColumnType(value = MySqlTypeConstant.VARCHAR , length = 64)
    @ColumnComment("执行器类型")
    private String executeType ;

    /**
     * JSON参数
     */
    @TableField("json_param")
    @ColumnType(value = MySqlTypeConstant.TEXT)
    @ColumnComment("JSON参数")
    private String jsonParam;

    /**
     * groovyScript
     */
    @TableField("groovy_script")
    @ColumnType(value = MySqlTypeConstant.TEXT)
    @ColumnComment("groovy脚本")
    private String groovyScript;

    /**
     * 访问权限
     */
    @TableField("use_count")
    @ColumnType(value = MySqlTypeConstant.INT, length = 5)
    @ColumnComment("调用次数")
    private Integer useCount = 0 ;
}
