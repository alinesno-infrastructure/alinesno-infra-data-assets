package com.alinesno.infra.data.assets.api;

import lombok.Data;

import java.util.List;

/**
 * 资产标签更新数据传输对象
 */
@Data
public class AssetTagUpdateDto {
    private Long id;                // 单条更新：manifest id
    private List<Long> ids;         // 批量更新：manifest id 列表
    private String assetTag;        // 要保存的 assetTag 字符串 (例如 "1:标签A,2:标签B")
}