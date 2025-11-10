package com.alinesno.infra.data.fastapi.gateway.provider;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.data.fastapi.entity.ApiClientEntity;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import com.alinesno.infra.data.fastapi.service.IApiConfigService;
import com.alinesno.infra.data.fastapi.service.IApiHandleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("${uriPrefix:/provider}") // 类级别使用占位符，可在配置文件中自定义
public class ApiProviderHandle extends BasePreHandle {

    @Autowired
    private IApiHandleService apiHandleService;

    @Autowired
    private IApiConfigService apiConfigService;

    @Autowired
    private IApiClientService apiClientService;

    @Value("${uriPrefix:/provider}")
    private String uriPrefix;

    @RequestMapping(value = "/**", method = {
            RequestMethod.DELETE,
            RequestMethod.GET,
            RequestMethod.PUT,
            RequestMethod.POST})
    public AjaxResult handle(HttpServletRequest request,
                             HttpServletResponse response) {

        String requestUri = request.getRequestURI();
        log.debug("requestUri = {}", requestUri);

        // 1. 先做过滤器处理(安全处理)，返回 client 并把 client/orgId 写入 request attribute
        ApiClientEntity client = apiClientService.validateClientToken(request);
        Long orgId = client.getOrgId();

        String relativePath = requestUri.substring(uriPrefix.length()) ;
        log.debug("resolved relativePath = {}", relativePath);

        // 4. 通过 orgId + relativePath 获取 ApiConfig 并处理
        ApiConfigEntity apiConfig = apiConfigService.getByUri(relativePath, orgId);
        Assert.notNull(apiConfig, relativePath + "找不到路径，请确认路径是否正确." );

        Object data = apiHandleService.handle(apiConfig, request, response, orgId);

        log.debug("data = {}", data);
        return AjaxResult.success(data);
    }
}