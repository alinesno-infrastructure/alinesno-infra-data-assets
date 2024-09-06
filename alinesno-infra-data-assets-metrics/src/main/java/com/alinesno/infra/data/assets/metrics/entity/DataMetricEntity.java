package com.alinesno.infra.data.assets.metrics.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.alinesno.infra.data.assets.metrics.handle.JsonTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("data_metrics")
public class DataMetricEntity extends InfraBaseEntity {

    @TableField
    @ColumnType(length = 128)
    @ColumnComment("指标名称")
    private String metricName;

    @TableField
    private Double value;

    @TableField
    private LocalDateTime timestamp;

    @TableField(typeHandler = JsonTypeHandler.class)
    private Map<String, String> dimension;

    @TableField
    private String description;

    @TableField
    @ColumnComment("更新策略")
    private String updatePolicy;

    @TableField(typeHandler = JsonTypeHandler.class)
    private Map<String, String> processingSql;
}