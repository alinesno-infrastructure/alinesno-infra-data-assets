package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import com.alinesno.infra.data.fastapi.service.IApiHandleService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理业务数据
 */
@Slf4j
@Service
public class ApiHandleServiceImpl implements IApiHandleService {

    @Autowired
    private IApiClientService apiClientService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows
    @DS("postgresql")
    @Override
    public Object handle(ApiConfigEntity apiConfig, HttpServletRequest request, HttpServletResponse response) {

        String scriptText = apiConfig.getGroovyScript() ;

        // 创建 ObjectMapper 实例
        ObjectMapper mapper = new ObjectMapper();

        // 从 request 中读取输入流
        Map<?,?> params = mapper.readValue(request.getInputStream(), Map.class);

        // 创建 Binding 对象，用于绑定变量到 Groovy 脚本
        Binding binding = new Binding();

        binding.setVariable("request", request); // 工具类
        binding.setVariable("response", response); // 工具类
        binding.setVariable("params", params); // 工具类
        binding.setVariable("jdbcTemplate", jdbcTemplate); // 工具类

        binding.setVariable("log", log); // 日志输出

        // 创建 GroovyShell 实例
        GroovyShell shell = new GroovyShell(this.getClass().getClassLoader(), binding);

        // 执行 Groovy 脚本
        try{
            return String.valueOf(shell.evaluate(scriptText)) ;
        }catch (Exception e){
            return "角色脚本执行失败:" + e.getMessage() ;
        }

    }

}
