package com.alinesno.infra.data.fastapi.gateway.provider;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.data.fastapi.service.IApiHandleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接收API接口服务
 */
@Slf4j
@RestController
@RequestMapping
public class ApiHandle extends BasePreHandle {

    @Autowired
    private IApiHandleService apiHandleService ;

    /**
     * 接收所有的数据api接口
     * @return
     */
    @RequestMapping(value = "/api/**" , method = {
            RequestMethod.DELETE ,
            RequestMethod.GET ,
            RequestMethod.PUT ,
            RequestMethod.POST })
    public AjaxResult handle(HttpServletRequest request ,
                             HttpServletResponse response){

        String uri = request.getRequestURI() ;
        log.debug("uri = {}" , uri);

        // 1. 先做过滤器处理(安全处理)

        // 2. 业务处理
        Object data = apiHandleService.handle() ;

        // 3. 再做插件处理(数据处理)

        return  AjaxResult.success(data) ;
    }

}
