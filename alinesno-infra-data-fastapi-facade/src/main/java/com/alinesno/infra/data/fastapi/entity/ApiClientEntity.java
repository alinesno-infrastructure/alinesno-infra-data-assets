package com.alinesno.infra.data.fastapi.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * API客户端
 */
@EqualsAndHashCode(callSuper = true)
@TableName("api_client")
@Data
public class ApiClientEntity extends InfraBaseEntity {

    /**
     * 客户端ID
     */
    @TableField("client_id")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 64)
    @ColumnComment("客户端ID")
    private String clientId;

    /**
     * 客户端名称
     */
    @TableField("client_name")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 64)
    @ColumnComment("客户端名称")
    private String clientName;

    /**
     * 备注
     */
    @TableField("remark")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 255)
    @ColumnComment("备注")
    private String remark;

    /**
     * 密钥
     */
    @TableField("secret")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 64)
    @ColumnComment("密钥")
    private String secret;

    /**
     * Token过期时间
     */
    @TableField("expiry_time")
    @ColumnType(value = MySqlTypeConstant.DATETIME)
    @ColumnComment("Token过期时间")
    private Date expiryTime;


    /**
     * 调用次数
     */
    @TableField("use_count")
    @ColumnType(value = MySqlTypeConstant.INT, length = 5)
    @ColumnComment("调用次数")
    private Integer useCount = 0 ;
}
