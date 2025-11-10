package com.alinesno.infra.data.fastapi.utils;

import com.alinesno.infra.data.fastapi.api.ApiConfigValidateDto;
import com.alinesno.infra.data.fastapi.entity.ApiClientEntity;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * 验证接口工具类
 */
@Component
public class ValidateApiUtils {

    @Value("${server.port}")
    private int port;

    @Autowired
    private IApiConfigService apiConfigService;

    @Autowired
    private IApiClientService apiClientService;

    @Value("${uriPrefix:/provider}")
    private String uriPrefix;

    /**
     * 验证接口（params 已经是 query-string 格式，如: "a=1&b=2" 或 "asdfsdfasdf&adfasdf=asdfsadf"）
     *
     * @param dto 包含 apiId 和 params
     * @return 接口响应内容字符串，可能为 null（若响应 body 为空）
     * @throws IOException 网络或请求异常
     */
    public String validateApiScript(ApiConfigValidateDto dto) throws IOException {

        String params = dto.getParams();

        ApiConfigEntity apiConfigEntity = apiConfigService.getById(dto.getApiId());
        ApiClientEntity apiClientEntity = apiClientService.getTokenForTest(apiConfigEntity.getOrgId());

        Assert.notNull(apiClientEntity, "未找到对应的客户端");

        OkHttpClient client = new OkHttpClient();

        // 构造基础 URL
        String baseUrl = "http://localhost:" + port + uriPrefix + apiConfigEntity.getPath();

        // 若 params 不为空，直接将其拼接为 query-string（假设 params 已经是正确的 query-string）
        String finalUrl = baseUrl;
        if (params != null && !params.isEmpty()) {
            // 确保不重复加 '?'
            String trimmed = params.startsWith("?") ? params.substring(1) : params;
            finalUrl = baseUrl + "?" + trimmed;
        }

        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .addHeader("Authorization", "Bearer " + apiClientEntity.getSecret())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // 根据需求可以抛异常或返回错误信息；这里抛出 IOException
                throw new IOException("Unexpected HTTP response: " + response.code() + " - " + response.message());
            }
            ResponseBody body = response.body();
            return body != null ? body.string() : null;
        }
    }
}