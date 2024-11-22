package com.alinesno.infra.data.assets.api;

import lombok.Data;

@Data
public class TableMetrics {
    private long existingDataCount; // 原有数据量
    private long newDataCount;      // 新增加的数据量
    private long totalDataCount;    // 当前总的数据量
}