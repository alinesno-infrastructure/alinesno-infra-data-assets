//groovyscript_api.js
import request from '@/utils/request'

/**
 * 添加规则组件动态脚本
 */
 export const addGroovyScript = data => {
    return request({
        url: '/groovyScript/add',
        method: 'post',
        data: data
    })
};

/**
 * 添加规则组件动态脚本
 */
export const updateGroovyScript = data => {
    return request({
        url: '/groovyScript/update',
        method: 'post',
        data: data
    })
};

/**
 * 查询规则组件动态脚本
 */
export const groovyScriptList = data => {
    return request({
        url: '/groovyScript/list',
        method: 'get',
        params: data
    })
};

/**
 * 删除规则组件动态脚本
 */
export const deleteGroovyScript = data => {
    return request({
        url: '/groovyScript/delete',
        method: 'get',
        params: data
    })
};

/**
 * 启用规则组件动态脚本
 */
 export const startGroovyScript = data => {
    return request({
        url: '/groovyScript/start',
        method: 'get',
        params: data
    })
};

/**
 * 停止规则组件动态脚本
 */
export const stopGroovyScript = data => {
    return request({
        url: '/groovyScript/stop',
        method: 'get',
        params: data
    })
};

/**
 * 启用规则组件动态脚本
 */
 export const upGroovyScript = data => {
    return request({
        url: '/groovyScript/up',
        method: 'get',
        params: data
    })
};

/**
 * 启用规则组件动态脚本
 */
 export const downGroovyScript = data => {
    return request({
        url: '/groovyScript/down',
        method: 'get',
        params: data
    })
};

/**
 * 规则组件GroovyScript示例代码
 */
export const groovyScriptCode = ()=>{
    let code = "package com.flying.fish.gateway.component.groovy;\n";
    code += " \n";
    code += "import com.flying.fish.formwork.base.BaseGroovyService;\n";
    code += "import com.flying.fish.formwork.util.NetworkIpUtils;\n";
    code += "import org.apache.commons.lang3.*;\n";
    code += "import org.slf4j.*;\n";
    code += "import org.springframework.http.HttpHeaders;\n";
    code += "import org.springframework.http.server.reactive.ServerHttpRequest;\n";
    code += "import org.springframework.web.server.ServerWebExchange;\n";
    code += "import java.util.Map;\n";
    code += "/**\n";
    code += " * @description\n";
    code += " * @author  admin\n";
    code += " * @version 1.0.0\n";
    code += "*/\n";
    code += "public class ParameterGroovyService extends BaseGroovyService {\n";
    code += "    private Logger log = LoggerFactory.getLogger(\"ParameterGroovyService\");\n";
    code += "    @Override\n";
    code += "    public void apply(ServerWebExchange exchange) throws Exception {\n";
    code += "        ServerHttpRequest request = exchange.getRequest();\n";
    code += "        HttpHeaders headers = exchange.getRequest().getHeaders();\n";
    code += "        String remoteAddr = NetworkIpUtils.getIpAddress(request);\n";
    code += "        \n";
    code += "        //routeId, ruleName, extednInfo从继承父类BaseGroovyService中获取\n";
    code += "        log.info(\"网关路由【{}】执行GroovySrcipt规则引擎动态脚本组件名称【{}】,扩展参数【{}】\", routeId, ruleName, extednInfo);\n";
    code += "        \n";
    code += "        Map<String,String> valueMap = request.getQueryParams().toSingleValueMap();\n";
    code += "        String userId = valueMap.get(\"userId\");\n";
    code += "        if (StringUtils.isBlank(userId)){\n";
    code += "            throw new IllegalArgumentException(\"缺少userId参数\");\n";
    code += "        }\n";
    code += "    }\n";
    code += "}";
    return code; 
};
