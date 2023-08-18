package com.cabin.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route("login_route", r -> r.path("/login")
//                        .uri("http://your-login-service/login"))
                .route("app_route", r -> r.path("/app/**")
                        .filters(f ->
                                        f.filter(new AuthFilter()).stripPrefix(1)
//                                f.stripPrefix(1) //测试是否可用
                        )
                        .uri("http://localhost:8080"))
                .build();
    }
}