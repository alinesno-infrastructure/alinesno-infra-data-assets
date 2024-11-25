package com.alinesno.infra.data.assets.api;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息传输对象类
 * 用于在系统间传输用户基本信息
 * 包含用户ID和组织ID，是用户身份的标识
 */
@NoArgsConstructor
@Data
public class PushDataUserInfoDto {

    /**
     * 用户ID，唯一标识一个用户
     */
    private String userId ;

    /**
     * 组织ID，标识用户所属的组织
     */
    private String orgId ;

    /**
     * 部门ID，标识用户所属的部门
     */
    private String departmentId ;

    public PushDataUserInfoDto(String userId, String orgId) {
        this.userId = userId;
        this.orgId = orgId;
    }

}

