package com.alinesno.infra.data.fastapi.api.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description
 * @author JL
 * @date 2020/07/09
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CountReq extends BaseReq implements java.io.Serializable {
    /**
     * 负载ID
     */
    private String balancedId;
    /**
     * 路由集合
     */
    private List<String> routeIds;
    /**
     * 统计类型
     */
    private String dateType;
    /**
     * 名称
     */
    private String name;
    /**
     * 日期
     */
    private String date;
    /**
     * 分组
     */
    private String groupCode;

    private Integer currentPage;
    private Integer pageSize;
}
