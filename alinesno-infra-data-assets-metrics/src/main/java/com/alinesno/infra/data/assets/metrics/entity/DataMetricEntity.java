package com.alinesno.infra.data.assets.metrics.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("data_metric")
@TableComment("数据资产指标")
public class DataMetricEntity extends InfraBaseEntity {

    @TableField
    @Column(type = MySqlTypeConstant.VARCHAR , length = 255 , comment = "指标名称")
    private String metricName;

    @TableField
    @Column(type = MySqlTypeConstant.DOUBLE , comment = "指标值")
    private Double value;

    @TableField
    @Column(type = MySqlTypeConstant.VARCHAR , length = 255 , comment = "维度")
    private String dimension;

    @TableField
    @Column(type = MySqlTypeConstant.VARCHAR , length = 255 , comment = "描述")
    private String description;

    @TableField
    @Column(type = MySqlTypeConstant.VARCHAR , length = 255 , comment = "更新策略")
    private String updatePolicy;

    @TableField
    @Column(type = MySqlTypeConstant.VARCHAR , length = 255 , comment = "处理SQL")
    private String processingSql;
}