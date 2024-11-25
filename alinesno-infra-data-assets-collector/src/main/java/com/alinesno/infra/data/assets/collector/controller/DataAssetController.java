package com.alinesno.infra.data.assets.collector.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.data.assets.api.PushDataUserInfoDto;
import com.alinesno.infra.data.assets.api.TableMetrics;
import com.alinesno.infra.data.assets.collector.aspect.PushAccess;
import com.alinesno.infra.data.assets.collector.service.DataAssetService;
import com.alinesno.infra.data.assets.key.KeyGenerator;
import jakarta.servlet.http.HttpServletRequest;
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
    private HttpServletRequest request ;

    @Autowired
    private DataAssetService dataAssetService;

    /**
     * 处理CSV文件上传请求
     *
     * @param file CSV格式的文件
     * @param model 数据模型标识
     * @return 操作结果的Ajax响应
     */
    @PushAccess
    @PostMapping("/csv")
    public AjaxResult handleCsvFile(@RequestParam("file") MultipartFile file,
                                    @RequestParam("model") String model) {

        PushDataUserInfoDto userInfoDto = genUserInfoDto() ;
        TableMetrics tableMetrics = dataAssetService.handleCsvFile(
                file,
                model ,
                userInfoDto);

        return AjaxResult.success("操作成功." ,tableMetrics);
    }

    /**
     * 处理JSON文件上传请求
     *
     * @param file JSON格式的文件
     * @param model 数据模型标识
     * @return 操作结果的Ajax响应
     */
    @PushAccess
    @PostMapping("/json")
    public AjaxResult handleJsonFile(@RequestParam("file") MultipartFile file,
                                     @RequestParam("model") String model) {

        PushDataUserInfoDto userInfoDto = genUserInfoDto() ;
        TableMetrics tableMetrics = dataAssetService.handleJsonFile(
                file,
                model,
                userInfoDto);

        return AjaxResult.success("操作成功." , tableMetrics);
    }

    /**
     * 处理列表数据请求
     *
     * @param dataList 列表形式的数据，每个元素为键值对集合
     * @param model 数据模型标识
     * @return 操作结果的Ajax响应
     */
    @PushAccess
    @PostMapping("/list")
    public AjaxResult handleListData(@RequestBody List<Map<String, Object>> dataList,
                                     @RequestParam("model") String model) {

        PushDataUserInfoDto userInfoDto = genUserInfoDto() ;
        TableMetrics tableMetrics = dataAssetService.handleListData(
                dataList,
                model,
                userInfoDto);

        return AjaxResult.success("数据插入成功." , tableMetrics);
    }

    private PushDataUserInfoDto genUserInfoDto() {
        String apiKey = request.getHeader(KeyGenerator.API_KEY) ;

        String[] ids = KeyGenerator.parseIdsFromKey(apiKey);
        String userId = ids[0] ;
        String orgId = ids[1] ;

        return new PushDataUserInfoDto(userId , orgId) ;
    }
}