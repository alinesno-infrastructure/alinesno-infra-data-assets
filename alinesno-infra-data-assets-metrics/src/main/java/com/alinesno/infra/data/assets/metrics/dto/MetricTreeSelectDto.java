package com.alinesno.infra.data.assets.metrics.dto;

import com.alinesno.infra.data.assets.metrics.entity.DataMetricTypeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Data
public class MetricTreeSelectDto implements Serializable {

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MetricTreeSelectDto> children;

    public MetricTreeSelectDto(DataMetricTypeEntity promptCatalog) {
        this.id = promptCatalog.getId() ;
        this.label = promptCatalog.getName();
        this.children = promptCatalog.getChildren().stream().map(MetricTreeSelectDto::new).collect(Collectors.toList());
    }

}
