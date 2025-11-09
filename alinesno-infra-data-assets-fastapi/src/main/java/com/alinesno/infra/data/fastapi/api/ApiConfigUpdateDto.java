package com.alinesno.infra.data.fastapi.api;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 更新 DTO（id 必填）
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiConfigUpdateDto extends ApiConfigBaseDto {

    @NotNull(message = "缺少接口ID")
    @Override
    public Long getId() {
        return super.getId();
    }
}