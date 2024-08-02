package com.alinesno.infra.data.fastapi.gateway.controller;

import cn.hutool.json.JSONUtil;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.SuperController;
import com.alinesno.infra.data.fastapi.api.dto.CountReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 统计管理界面
 * @author luoxiaodong
 * @version 1.0.0
 */
@RestController
@RequestMapping("/count")
public class CountController extends SuperController {

//    /**
//     * 负载类型的请求（需要在缓存filed前加一个balanced前缀，用于区分普通路由ID）
//     * @param countReq
//     * @return
//     */
//    @RequestMapping(value = "/balanced/request", method = {RequestMethod.GET, RequestMethod.POST})
//    public AjaxResult balancedRequest(@RequestBody CountReq countReq) {
//        return countService.count(countReq, true);
//    }
//
//    /**
//     * 请求
//     * @param countReq
//     * @return
//     */
//    @RequestMapping(value = "/request", method = {RequestMethod.GET, RequestMethod.POST})
//    public AjaxResult request(@RequestBody CountReq countReq) {
//        return countService.count(countReq, false);
//    }

    /**
     * 查询路由集合统计结果
     * @param countReq
     * @return
     */
    @RequestMapping(value = "/route/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public Object routePageList(@RequestBody CountReq countReq) {

        String data = "{\n" +
                "    \"code\": 200,\n" +
                "    \"timestamp\": \"2024-04-06T23:40:12.541+00:00\",\n" +
                "    \"msg\": \"\",\n" +
                "    \"data\": {\n" +
                "        \"totalNum\": 9,\n" +
                "        \"currentPage\": 1,\n" +
                "        \"pageSize\": 10,\n" +
                "        \"lists\": [\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"0b4c045969744a49a45494c26587f0d0\",\n" +
                "                \"routeId\": \"base-notice\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"base-notice-基础通知服务\",\n" +
                "                \"systemCode\": \"base-notice\",\n" +
                "                \"groupCode\": \"interior_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-base-notice-boot:8080\",\n" +
                "                \"path\": \"/base-notice/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2024-02-12 10:05:15\",\n" +
                "                \"updateTime\": \"2024-02-12T02:05:15.856+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"31a05f3e8071462385c45ef989e144a6\",\n" +
                "                \"routeId\": \"ops-container\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"ops-container-容器管理服务\",\n" +
                "                \"systemCode\": \"ops-container\",\n" +
                "                \"groupCode\": \"interior_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-ops-container-boot:8080\",\n" +
                "                \"path\": \"/ops-container/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2024-01-20 16:17:35\",\n" +
                "                \"updateTime\": \"2024-01-20T08:17:35.940+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"277301d854894bd383cc6d3f63c0c158\",\n" +
                "                \"routeId\": \"smart-embedding\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"smart-embedding-向量转换服务\",\n" +
                "                \"systemCode\": \"smart-embedding\",\n" +
                "                \"groupCode\": \"external_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-base-search-embedding-application.beta.ai.infra.linesno.com\",\n" +
                "                \"path\": \"/smart-embedding/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2024-01-01 07:13:16\",\n" +
                "                \"updateTime\": \"2023-12-31T23:13:16.587+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"95588bab666b40ab865b9b9fa7805014\",\n" +
                "                \"routeId\": \"base-storage\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"base-storage-分布式存储服务\",\n" +
                "                \"systemCode\": \"base-storage\",\n" +
                "                \"groupCode\": \"interior_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-base-storage-boot:8080\",\n" +
                "                \"path\": \"/base-storage/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2023-12-30 04:55:38\",\n" +
                "                \"updateTime\": \"2023-12-29T20:55:38.892+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"31ed424fe419402598dc8474ad515e94\",\n" +
                "                \"routeId\": \"base-search\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"base-search-通用搜索服务\",\n" +
                "                \"systemCode\": \"base-search\",\n" +
                "                \"groupCode\": \"interior_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-base-search-boot:8080\",\n" +
                "                \"path\": \"/base-search/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2023-12-30 04:53:47\",\n" +
                "                \"updateTime\": \"2024-01-08T12:13:09.755+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"6085ba9985e64025898da507b0b6e8ff\",\n" +
                "                \"routeId\": \"smart-brain\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"smart-brain-智能推理服务\",\n" +
                "                \"systemCode\": \"smart-brain\",\n" +
                "                \"groupCode\": \"interior_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-smart-brain-boot:8080\",\n" +
                "                \"path\": \"/smart-brain/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2023-12-30 04:19:08\",\n" +
                "                \"updateTime\": \"2023-12-29T20:19:08.216+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"6049d9d2d954463e9433aaddbf2e01eb\",\n" +
                "                \"routeId\": \"smart-assistant\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"smart-assistant-智能助手服务\",\n" +
                "                \"systemCode\": \"smart-assistant\",\n" +
                "                \"groupCode\": \"interior_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-smart-assistant-boot:8080\",\n" +
                "                \"path\": \"/smart-assistant/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2023-12-30 04:17:50\",\n" +
                "                \"updateTime\": \"2023-12-29T20:17:50.857+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"58d754c4051e44f3aa49f01899499517\",\n" +
                "                \"routeId\": \"base-authority\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"base-authority-权限引擎服务\",\n" +
                "                \"systemCode\": \"base-authority\",\n" +
                "                \"groupCode\": \"interior_api\",\n" +
                "                \"uri\": \"http://alinesno-infra-base-authority-boot:8080\",\n" +
                "                \"path\": \"/base-authority/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2023-12-24 20:40:41\",\n" +
                "                \"updateTime\": \"2023-12-24T12:47:46.217+00:00\",\n" +
                "                \"count\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"operatorId\": null,\n" +
                "                \"id\": \"2dd8182983714695be09cfba56511f1f\",\n" +
                "                \"routeId\": \"Test-Project\",\n" +
                "                \"accountToken\": \"06f125d229034aa3bd243a8def69e75f\",\n" +
                "                \"name\": \"Test-测试网关\",\n" +
                "                \"systemCode\": \"Test\",\n" +
                "                \"groupCode\": \"external_api\",\n" +
                "                \"uri\": \"http://v.juhe.cn\",\n" +
                "                \"path\": \"/juhe/**\",\n" +
                "                \"method\": null,\n" +
                "                \"host\": \"\",\n" +
                "                \"remoteAddr\": \"\",\n" +
                "                \"header\": \"\",\n" +
                "                \"filterGatewaName\": \"\",\n" +
                "                \"filterHystrixName\": null,\n" +
                "                \"filterRateLimiterName\": null,\n" +
                "                \"filterAuthorizeName\": \"\",\n" +
                "                \"fallbackMsg\": \"\",\n" +
                "                \"fallbackTimeout\": \"0\",\n" +
                "                \"replenishRate\": 20,\n" +
                "                \"burstCapacity\": 100,\n" +
                "                \"weightName\": null,\n" +
                "                \"weight\": null,\n" +
                "                \"status\": \"0\",\n" +
                "                \"stripPrefix\": 1,\n" +
                "                \"requestParameter\": \"\",\n" +
                "                \"rewritePath\": \"\",\n" +
                "                \"accessHeader\": \"\",\n" +
                "                \"accessIp\": \"\",\n" +
                "                \"accessParameter\": \"\",\n" +
                "                \"accessTime\": \"\",\n" +
                "                \"accessCookie\": \"\",\n" +
                "                \"createTime\": \"2023-12-22 20:31:36\",\n" +
                "                \"updateTime\": \"2023-12-22T12:31:36.258+00:00\",\n" +
                "                \"count\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"data\": null\n" +
                "    }\n" +
                "}";

        return JSONUtil.parse(data) ;
    }

    /**
     * 统计按7天和按24小时维度计算的请求总量
     * @return
     */
    @RequestMapping(value = "/request/total", method = {RequestMethod.GET, RequestMethod.POST})
    public Object routeTotal() {
        return routeAppTotal(null) ;
    }

    /**
     * 统计按7天和按24小时维度计算的请求总量
     * @return
     */
    @RequestMapping(value = "/request/app/total", method = {RequestMethod.GET, RequestMethod.POST})
    public Object routeAppTotal(@RequestParam(required=false) String id) {

        if(StringUtils.isNoneBlank(id)){
            String d = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"timestamp\": \"2024-04-06T23:52:10.346+00:00\",\n" +
                    "    \"msg\": \"\",\n" +
                    "    \"data\": {\n" +
                    "        \"dayCounts\": {\n" +
                    "            \"dates\": [\n" +
                    "                \"20240401\",\n" +
                    "                \"20240402\",\n" +
                    "                \"20240403\",\n" +
                    "                \"20240404\",\n" +
                    "                \"20240405\",\n" +
                    "                \"20240406\",\n" +
                    "                \"20240407\"\n" +
                    "            ],\n" +
                    "            \"datas\": null,\n" +
                    "            \"counts\": [\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"hourCounts\": {\n" +
                    "            \"dates\": [\n" +
                    "                \"08\",\n" +
                    "                \"09\",\n" +
                    "                \"10\",\n" +
                    "                \"11\",\n" +
                    "                \"12\",\n" +
                    "                \"13\",\n" +
                    "                \"14\",\n" +
                    "                \"15\",\n" +
                    "                \"16\",\n" +
                    "                \"17\",\n" +
                    "                \"18\",\n" +
                    "                \"19\",\n" +
                    "                \"20\",\n" +
                    "                \"21\",\n" +
                    "                \"22\",\n" +
                    "                \"23\",\n" +
                    "                \"00\",\n" +
                    "                \"01\",\n" +
                    "                \"02\",\n" +
                    "                \"03\",\n" +
                    "                \"04\",\n" +
                    "                \"05\",\n" +
                    "                \"06\",\n" +
                    "                \"07\"\n" +
                    "            ],\n" +
                    "            \"datas\": null,\n" +
                    "            \"counts\": [\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"minCounts\": {\n" +
                    "            \"dates\": [\n" +
                    "                \"53\",\n" +
                    "                \"54\",\n" +
                    "                \"55\",\n" +
                    "                \"56\",\n" +
                    "                \"57\",\n" +
                    "                \"58\",\n" +
                    "                \"59\",\n" +
                    "                \"00\",\n" +
                    "                \"01\",\n" +
                    "                \"02\",\n" +
                    "                \"03\",\n" +
                    "                \"04\",\n" +
                    "                \"05\",\n" +
                    "                \"06\",\n" +
                    "                \"07\",\n" +
                    "                \"08\",\n" +
                    "                \"09\",\n" +
                    "                \"10\",\n" +
                    "                \"11\",\n" +
                    "                \"12\",\n" +
                    "                \"13\",\n" +
                    "                \"14\",\n" +
                    "                \"15\",\n" +
                    "                \"16\",\n" +
                    "                \"17\",\n" +
                    "                \"18\",\n" +
                    "                \"19\",\n" +
                    "                \"20\",\n" +
                    "                \"21\",\n" +
                    "                \"22\",\n" +
                    "                \"23\",\n" +
                    "                \"24\",\n" +
                    "                \"25\",\n" +
                    "                \"26\",\n" +
                    "                \"27\",\n" +
                    "                \"28\",\n" +
                    "                \"29\",\n" +
                    "                \"30\",\n" +
                    "                \"31\",\n" +
                    "                \"32\",\n" +
                    "                \"33\",\n" +
                    "                \"34\",\n" +
                    "                \"35\",\n" +
                    "                \"36\",\n" +
                    "                \"37\",\n" +
                    "                \"38\",\n" +
                    "                \"39\",\n" +
                    "                \"40\",\n" +
                    "                \"41\",\n" +
                    "                \"42\",\n" +
                    "                \"43\",\n" +
                    "                \"44\",\n" +
                    "                \"45\",\n" +
                    "                \"46\",\n" +
                    "                \"47\",\n" +
                    "                \"48\",\n" +
                    "                \"49\",\n" +
                    "                \"50\",\n" +
                    "                \"51\",\n" +
                    "                \"52\"\n" +
                    "            ],\n" +
                    "            \"datas\": null,\n" +
                    "            \"counts\": [\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0,\n" +
                    "                0\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    }\n" +
                    "}" ;

            return JSONUtil.parse(d) ;
        }

        String data = "{\n" +
                "    \"code\": 200,\n" +
                "    \"timestamp\": \"2024-04-06T23:40:12.547+00:00\",\n" +
                "    \"msg\": \"\",\n" +
                "    \"data\": {\n" +
                "        \"dayCounts\": {\n" +
                "            \"dates\": [\n" +
                "                \"20240401\",\n" +
                "                \"20240402\",\n" +
                "                \"20240403\",\n" +
                "                \"20240404\",\n" +
                "                \"20240405\",\n" +
                "                \"20240406\",\n" +
                "                \"20240407\"\n" +
                "            ],\n" +
                "            \"datas\": null,\n" +
                "            \"counts\": [\n" +
                "                3,\n" +
                "                4,\n" +
                "                0,\n" +
                "                0,\n" +
                "                10,\n" +
                "                0,\n" +
                "                0\n" +
                "            ]\n" +
                "        },\n" +
                "        \"hourCounts\": {\n" +
                "            \"dates\": [\n" +
                "                \"08\",\n" +
                "                \"09\",\n" +
                "                \"10\",\n" +
                "                \"11\",\n" +
                "                \"12\",\n" +
                "                \"13\",\n" +
                "                \"14\",\n" +
                "                \"15\",\n" +
                "                \"16\",\n" +
                "                \"17\",\n" +
                "                \"18\",\n" +
                "                \"19\",\n" +
                "                \"20\",\n" +
                "                \"21\",\n" +
                "                \"22\",\n" +
                "                \"23\",\n" +
                "                \"00\",\n" +
                "                \"01\",\n" +
                "                \"02\",\n" +
                "                \"03\",\n" +
                "                \"04\",\n" +
                "                \"05\",\n" +
                "                \"06\",\n" +
                "                \"07\"\n" +
                "            ],\n" +
                "            \"datas\": null,\n" +
                "            \"counts\": [\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0\n" +
                "            ]\n" +
                "        },\n" +
                "        \"minCounts\": null\n" +
                "    }\n" +
                "}" ;

        return JSONUtil.parse(data) ;
    }

    /**
     * 流量
     * @param routeIds
     * @return
     */
    @RequestMapping(value = "/flux", method = {RequestMethod.GET, RequestMethod.POST})
    public AjaxResult flux(@RequestParam String [] routeIds ) {
        Assert.isTrue(routeIds != null, "未获取到对象ID");
        return new AjaxResult();
    }

}
