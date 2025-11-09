package com.alinesno.infra.data.fastapi.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ApiHistoryEntity类
 */
@EqualsAndHashCode(callSuper = true)
@TableName("api_history")
@Data
@TableComment("API接口历史表")
public class ApiHistoryEntity extends InfraBaseEntity {

    /**
     * API ID
     */
    @TableField("api_id")
    @ColumnType(length=32)
    @ColumnComment("AppID")
    private Long apiId;

    /**
     * Context
     */
    @TableField("context")
    @ColumnType(length=1024)
    @ColumnComment("内容")
    private String context;

}
