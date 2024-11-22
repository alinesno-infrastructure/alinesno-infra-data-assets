package com.alinesno.infra.data.assets.config;

import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import com.alinesno.infra.common.web.log.aspect.LogAspect;
import com.alinesno.infra.data.assets.sample.ISimpleService;
import com.dtflys.forest.springboot.annotation.ForestScan;
import jakarta.servlet.MultipartConfigElement;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

/**
 * 服务配置
 */
@Slf4j
@EnableActable
@EnableInfraSsoApi
@ForestScan({
        "com.alinesno.infra.common.web.adapter.base.consumer" ,
        "com.alinesno.infra.smart.assistant.adapter"
})
@MapperScan(basePackages = {
        "com.alinesno.infra.data.**.mapper" ,
})
@ComponentScan(basePackages = {
        "com.alinesno.infra.data.assets" ,
        "com.alinesno.infra.data.fastapi"
})
@Configuration
public class AppConfiguration implements CommandLineRunner {

    @Bean
    public MultipartConfigElement getMultipartConfig() {
        MultipartConfigFactory config = new MultipartConfigFactory() ;
        config.setMaxFileSize(DataSize.parse("200MB")); 		// 设置上传文件的单个大小限制
        config.setMaxRequestSize(DataSize.parse("500MB")); 		// 设置总的上传的大小限制
        config.setLocation(System.getProperty("java.io.tmpdir")); 	// 设置临时保存目录
        return config.createMultipartConfig() ;	// 创建一个上传配置
    }

    @Autowired
    private ISimpleService simpleService ;

    @Bean
    public LogAspect getLogAspect(){
        return new LogAspect() ;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("服务应用启动.");
    }

}
