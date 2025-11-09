package com.alinesno.infra.data.fastapi.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IpRuleEntity类
 */
@EqualsAndHashCode(callSuper = true)
@TableName("ip_rule")
@Data
@TableComment("API接口安全规则表")
public class IpRuleEntity extends InfraBaseEntity {

    /**
     * 状态
     */
    @TableField("type")
    @Column(comment = "类型", type = MySqlTypeConstant.VARCHAR, length = 8)
    private String type;

    /**
     * 模式
     */
    @TableField("ip")
    @Column(comment = "模式", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String ip;
}
