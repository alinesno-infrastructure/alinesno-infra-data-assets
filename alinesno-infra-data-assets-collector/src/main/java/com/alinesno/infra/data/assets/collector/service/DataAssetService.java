package com.alinesno.infra.data.assets.collector.service;

import com.alinesno.infra.data.assets.api.TableMetrics;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 数据资产服务接口，提供数据处理功能
 * 该接口定义了如何处理不同格式的文件数据和列表数据，将其转换为表格指标对象
 */
public interface DataAssetService {
    /**
     * 处理CSV文件数据
     *
     * @param file CSV文件，包含数据资产信息
     * @param model 模型名称，用于指定数据处理的方式或规则
     * @return TableMetrics 表格指标对象，包含处理后的数据信息
     */
    TableMetrics handleCsvFile(MultipartFile file, String model);

    /**
     * 处理JSON文件数据
     *
     * @param file JSON文件，包含数据资产信息
     * @param model 模型名称，用于指定数据处理的方式或规则
     * @return TableMetrics 表格指标对象，包含处理后的数据信息
     */
    TableMetrics handleJsonFile(MultipartFile file, String model);

    /**
     * 处理列表数据
     *
     * @param dataList 数据列表，每个元素为一个Map，代表一行数据
     * @param model 模型名称，用于指定数据处理的方式或规则
     * @return TableMetrics 表格指标对象，包含处理后的数据信息
     */
    TableMetrics handleListData(List<Map<String, String>> dataList, String model);
}
