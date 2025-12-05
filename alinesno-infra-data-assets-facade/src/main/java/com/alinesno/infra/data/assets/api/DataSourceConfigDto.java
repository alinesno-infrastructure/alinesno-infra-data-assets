package com.alinesno.infra.data.assets.api;

import com.alinesno.infra.common.facade.base.BaseDto;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * 数据源配置数据传输对象
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
public class DataSourceConfigDto extends BaseDto {

    @NotBlank(message = "数据源名称不能为空")
    private String name;

    @NotBlank(message = "数据源类型不能为空")
    private String type;

    @NotBlank(message = "连接地址不能为空")
    private String url;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Integer connectionTimeout;

    private String remark;

    /**
     * 转换为实体对象
     * @return
     */
    public DataSourceConfigEntity toEntity(String driver) {
        DataSourceConfigEntity entity = new DataSourceConfigEntity();

        BeanUtils.copyProperties(this, entity);

        entity.setDriverClassName(driver);
        entity.setName(this.name);
        entity.setType(this.type);
        entity.setUrl(this.url);
        entity.setUsername(this.username);
        entity.setPassword(this.password);
        entity.setConnectionTimeout(this.connectionTimeout);
        entity.setRemark(this.remark);

        return entity ;
    }

    /**
     * 从实体转换为DTO（自动脱敏密码，供前端展示）
     * @param dataSourceConfigEntity 数据库实体
     * @return 脱敏后的DTO
     */
    public static DataSourceConfigDto fromEntity(DataSourceConfigEntity dataSourceConfigEntity) {
        // 空值判断
        if (dataSourceConfigEntity == null) {
            return null;
        }

        DataSourceConfigDto dto = new DataSourceConfigDto();
        // 复制基础字段（ID、创建时间等继承自BaseDto的字段也会被复制）
        BeanUtils.copyProperties(dataSourceConfigEntity, dto);

        // 单独处理密码脱敏（核心：避免返回明文）
        dto.setPassword(desensitizePassword(dataSourceConfigEntity.getPassword()));

        // 补充字段（若BeanUtils未覆盖，可手动赋值）
        dto.setName(dataSourceConfigEntity.getName());
        dto.setType(dataSourceConfigEntity.getType());
        dto.setUrl(dataSourceConfigEntity.getUrl());
        dto.setUsername(dataSourceConfigEntity.getUsername());
        dto.setConnectionTimeout(dataSourceConfigEntity.getConnectionTimeout());
        dto.setRemark(dataSourceConfigEntity.getRemark());

        return dto;
    }

    /**
     * 密码脱敏工具方法
     * 规则：
     * 1. 空密码返回空字符串
     * 2. 长度≤2：全部替换为*
     * 3. 长度>2：保留前2位，其余替换为****
     */
    private static String desensitizePassword(String rawPassword) {
        if (!StringUtils.hasText(rawPassword)) {
            return "";
        }
        int length = rawPassword.length();
        if (length <= 2) {
            return "*".repeat(length);
        }
        // 示例：root → ro**** | 123456 → 12****
        return rawPassword.substring(0, 2) + "****";
    }

}