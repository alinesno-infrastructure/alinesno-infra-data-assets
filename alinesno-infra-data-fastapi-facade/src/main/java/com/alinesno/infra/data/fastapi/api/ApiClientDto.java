package com.alinesno.infra.data.fastapi.api;

import com.alinesno.infra.common.facade.base.BaseDto;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * API客户端
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiClientDto extends BaseDto {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端名称
     */
    @NotBlank(message = "Client Name cannot be blank.")
    private String clientName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 密钥
     */
    @NotBlank(message = "Secret cannot be blank.")
    private String secret;

    /**
     * Token过期时间
     */
    @NotNull(message = "Expiry Time cannot be null.")
    private String expiryTimeStr ;

    /**
     * 调用次数
     */
    private Integer useCount = 0;
}
