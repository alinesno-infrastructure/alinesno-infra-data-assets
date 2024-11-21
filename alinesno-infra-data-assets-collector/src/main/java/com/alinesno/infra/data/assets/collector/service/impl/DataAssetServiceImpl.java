package com.alinesno.infra.data.assets.collector.service.impl;

import com.alinesno.infra.data.assets.collector.service.DataAssetService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class DataAssetServiceImpl implements DataAssetService {

    @Override
    public void handleCsvFile(MultipartFile file, String model) {
        // 实现CSV文件处理逻辑
        // 可以使用OpenCSV等库来解析CSV文件
        System.out.println("Handling CSV file for model: " + model);
    }

    @Override
    public void handleJsonFile(MultipartFile file, String model) {
        // 实现JSON文件处理逻辑
        // 可以使用Jackson或Gson等库来解析JSON文件
        System.out.println("Handling JSON file for model: " + model);
    }

    @Override
    public void handleListData(List<Map<String, String>> dataList, String model) {
        // 实现List数据处理逻辑
        System.out.println("Handling list data for model: " + model);
    }
}