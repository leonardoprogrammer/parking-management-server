package com.parkingmanagement.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user_service", r -> r.path("/api/users/**")
                        .uri("http://localhost:8081"))
                .route("parking_service", r -> r.path("/api/parking-lots/**")
                        .uri("http://localhost:8082"))
                .route("vehicle_service", r -> r.path("/api/vehicles/**")
                        .uri("http://localhost:8083"))
                .route("payment_service", r -> r.path("/api/payments/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}
