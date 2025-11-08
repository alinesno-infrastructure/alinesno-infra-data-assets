package com.alinesno.infra.data.assets.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 数据源配置表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("data_source_config")
@Table(comment = "数据源配置表")
public class DataSourceConfigEntity extends InfraBaseEntity {

    @TableField
    @Column(comment = "数据源名称", length = 100, isNull = false)
    private String name;

    @TableField
    @Column(comment = "数据源类型(clickhouse/mysql/postgresql)", length = 50, isNull = false)
    private String type;

    @TableField
    @Column(comment = "数据库连接地址", length = 255, isNull = false)
    private String url;

    @TableField
    @Column(comment = "用户名", length = 100, isNull = false)
    private String username;

    @TableField
    @Column(comment = "密码", length = 100, isNull = false)
    private String password;

    @TableField
    @Column(comment = "驱动类名", length = 255)
    private String driverClassName;

    @TableField
    @Column(comment = "连接超时时间(毫秒)", defaultValue = "30000")
    private Integer connectionTimeout;

    @TableField
    @Column(comment = "备注", length = 500)
    private String remark;

    // 最后同步时间 lastSyncTime
    @TableField
    @Column(comment = "最后同步时间")
    private Date lastSyncTime;

}