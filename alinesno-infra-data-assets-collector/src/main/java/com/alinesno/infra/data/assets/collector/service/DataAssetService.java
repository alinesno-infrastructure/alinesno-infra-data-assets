package com.alinesno.infra.data.assets.collector.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface DataAssetService {
    void handleCsvFile(MultipartFile file, String model);
    void handleJsonFile(MultipartFile file, String model);
    void handleListData(List<Map<String, String>> dataList, String model);
}
