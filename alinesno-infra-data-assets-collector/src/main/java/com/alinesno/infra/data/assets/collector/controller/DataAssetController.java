package com.alinesno.infra.data.assets.collector.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.data.assets.api.TableMetrics;
import com.alinesno.infra.data.assets.collector.service.DataAssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 数据资产采集器控制器
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/assets/collector")
public class DataAssetController {

    @Autowired
    private DataAssetService dataAssetService;

    /**
     * 处理CSV文件上传请求
     *
     * @param file CSV格式的文件
     * @param model 数据模型标识
     * @return 操作结果的Ajax响应
     */
    @PostMapping("/csv")
    public AjaxResult handleCsvFile(@RequestParam("file") MultipartFile file,
                                    @RequestParam("model") String model) {
        TableMetrics tableMetrics = dataAssetService.handleCsvFile(file, model);
        return AjaxResult.success("操作成功." ,tableMetrics);
    }

    /**
     * 处理JSON文件上传请求
     *
     * @param file JSON格式的文件
     * @param model 数据模型标识
     * @return 操作结果的Ajax响应
     */
    @PostMapping("/json")
    public AjaxResult handleJsonFile(@RequestParam("file") MultipartFile file,
                                     @RequestParam("model") String model) {
        TableMetrics tableMetrics =  dataAssetService.handleJsonFile(file, model);
        return AjaxResult.success("操作成功." , tableMetrics);
    }

    /**
     * 处理列表数据请求
     *
     * @param dataList 列表形式的数据，每个元素为键值对集合
     * @param model 数据模型标识
     * @return 操作结果的Ajax响应
     */
    @PostMapping("/list")
    public AjaxResult handleListData(@RequestBody List<Map<String, String>> dataList,
                                     @RequestParam("model") String model) {
        TableMetrics tableMetrics = dataAssetService.handleListData(dataList, model);
        return AjaxResult.success("数据插入成功." , tableMetrics);
    }
}