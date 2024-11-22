package com.alinesno.infra.data.assets.collector.service;

import com.alinesno.infra.data.assets.api.TableMetrics;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DataAssetService {
    TableMetrics handleCsvFile(MultipartFile file, String model);
    TableMetrics handleJsonFile(MultipartFile file, String model);
    TableMetrics handleListData(List<Map<String, String>> dataList, String model);
}
