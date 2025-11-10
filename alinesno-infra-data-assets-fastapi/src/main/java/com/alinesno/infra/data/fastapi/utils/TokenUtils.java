package com.alinesno.infra.data.fastapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 尝试从 token 中抽取 orgId：
     * 1) 若 token 看起来像 JWT（x.y.z），则解码 payload，查找 orgId 字段；
     * 2) 否则尝试 pattern orgId=123 或 orgId:123；
     */
    public static Long extractOrgIdFromToken(String token) {
        try {
            if (token == null) return null;
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String payload = parts[1];
                byte[] decoded = Base64.getUrlDecoder().decode(payload);
                @SuppressWarnings("unchecked")
                Map<String, Object> map = MAPPER.readValue(decoded, Map.class);
                Object v = map.get("orgId");
                if (v == null) v = map.get("org_id");
                if (v != null) return Long.valueOf(v.toString());
            }
            Pattern p = Pattern.compile("orgId[:=](\\d+)");
            Matcher m = p.matcher(token);
            if (m.find()) return Long.valueOf(m.group(1));
        } catch (Exception e) {
            // 忽略解析异常，返回 null
        }
        return null;
    }
}