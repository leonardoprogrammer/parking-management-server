package com.parkingmanagement.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r.path("/auth/**")
                        .uri("lb://AUTH"))
                .route("parkingmanagement", r -> r.path("/parkingmanagement/**")
                        .uri("lb://PARKING"))
                .route("parkedvehicles", r -> r.path("/parkedvehicles/**")
                        .uri("lb://PARKEDVEHICLE"))
                .build();
    }
}
