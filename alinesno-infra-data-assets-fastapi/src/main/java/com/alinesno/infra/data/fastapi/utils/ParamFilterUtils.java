package com.alinesno.infra.data.fastapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

public class ParamFilterUtils {

    private static final ObjectMapper M = new ObjectMapper();

    /**
     * jsonParamsJson 是 apiConfig.getJsonParam()，格式为 JSON 数组，每个元素至少包含 name 字段
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> filterByDefinition(String jsonParamsJson, Map<String, Object> inputParams) {
        if (jsonParamsJson == null || jsonParamsJson.isBlank()) return inputParams;
        try {
            List<Map<String, Object>> defs = M.readValue(jsonParamsJson, List.class);
            Set<String> allowed = defs.stream()
                    .map(m -> m.get("name"))
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toSet());

            Map<String, Object> out = new HashMap<>();
            for (Map.Entry<String, Object> e : inputParams.entrySet()) {
                if (allowed.contains(e.getKey())) {
                    out.put(e.getKey(), e.getValue());
                }
            }
            return out;
        } catch (Exception e) {
            // 解析异常则不过滤
            return inputParams;
        }
    }
}