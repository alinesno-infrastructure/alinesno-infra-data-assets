package com.alinesno.infra.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

@RestController
@SpringBootApplication
public class GatewayApplication {

    @Value("${alinesno.infra.backend}")
    private String backend ;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

     //在spring cloud gateway中使用RouteLocator的Bean进行路由转发,等价于在yml配置文件中的配置
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder){

        return builder.routes()
                //id 表示被转发到uri地址的id名，
                .route("csdn",p -> p
                        //predicates
                        .path("/csdn")
                        .uri("https://blog.csdn.net"))
                // prod-backend
                .route("service-backend",p -> p
                        //predicates
                        .path("/prod-api/**")
                        .uri(backend))

                .build();
    }

    private Predicate<ServerWebExchange> create404Predicate() {
        return exchange -> {
            int statusCode = exchange.getResponse().getStatusCode().value();

            System.out.println("statusCode = " + statusCode);

            return statusCode == 404;
        };
    }

}
