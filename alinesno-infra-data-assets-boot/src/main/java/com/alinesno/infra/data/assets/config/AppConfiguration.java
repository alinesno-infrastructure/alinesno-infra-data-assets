package com.alinesno.infra.data.assets.config;

import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import com.alinesno.infra.common.web.log.aspect.LogAspect;
import com.alinesno.infra.data.assets.sample.ISimpleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务配置
 */
@EnableActable
@MapperScan("com.alinesno.infra.data.assets.mapper")
@EnableInfraSsoApi
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

        simpleService.catalog();

    }
}
