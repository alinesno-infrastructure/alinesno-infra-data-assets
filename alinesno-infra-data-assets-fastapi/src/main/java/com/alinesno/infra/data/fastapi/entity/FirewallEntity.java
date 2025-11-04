package com.alinesno.infra.data.fastapi.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 防火墙实体类
 */
@EqualsAndHashCode(callSuper = true)
@TableName("firewall")
@Data
public class FirewallEntity extends InfraBaseEntity {

    /**
     * 状态
     */
    @TableField("status")
    @Column(comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 8)
    private String status;

    /**
     * 模式
     */
    @TableField("mode")
    @Column(comment = "模式", type = MySqlTypeConstant.VARCHAR, length = 8)
    private String mode;

}