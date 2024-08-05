package com.alinesno.infra.data.assets.config;

import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import com.alinesno.infra.common.web.log.aspect.LogAspect;
import com.alinesno.infra.data.assets.sample.ISimpleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 服务配置
 */
@EnableActable
@MapperScan(basePackages = {"com.alinesno.infra.data.assets.mapper" , "com.alinesno.infra.data.fastapi.mapper"})
@EnableInfraSsoApi
@ComponentScan(basePackages = {"com.alinesno.infra.data.assets" , "com.alinesno.infra.data.fastapi"})
@Configuration
public class AppConfiguration implements CommandLineRunner {

    @Autowired
    private ISimpleService simpleService ;

    @Bean
    public LogAspect getLogAspect(){
        return new LogAspect() ;
    }

    @Override
    public void run(String... args) throws Exception {

        // 初始化资产目录
        simpleService.catalog();

        // 初始化标签分类
        simpleService.label();

        // 示例资产数据
        simpleService.data();

    }
}
