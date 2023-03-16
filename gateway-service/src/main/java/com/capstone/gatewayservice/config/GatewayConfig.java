package com.capstone.gatewayservice.config;

import com.capstone.gatewayservice.filter.AppFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final AppFilter filter;
    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                // aurh service
                .route(r -> r.path("/api/v1/auth/login")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://auth-service:8081/"))
                // management user service
                .route(r -> r.path("/v1/api/users/**")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://management-user-service:8082/"))
                // item service
                .route(r -> r.path("/api/v1/items/**")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://item-service:8083/"))
                .route(r -> r.path("/api/v1/services/**")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://item-service:8083/"))
                // request service
                .route(r -> r.path("/v1/api/requests/**")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://request-service:8084/"))
                .route(r -> r.path("/v1/api/request-details/**")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://request-service:8084/"))
                .route(r -> r.path("/v1/api/requesttypes/**")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://request-service:8084/"))
                // approval service
                .route(r -> r.path("/api/v1/approvals/**")
                        .and().method("GET", "POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter))
                        .uri("http://approval-service:8085/"))
                .build();
    }
}
