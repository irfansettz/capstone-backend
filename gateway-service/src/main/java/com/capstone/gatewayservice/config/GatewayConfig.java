package com.capstone.gatewayservice.config;

import com.capstone.gatewayservice.exception.UnauthorizationException;
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
        try {
            return builder.routes()
                    .route(r -> r.path("/v1/api/users/**")
                            .and().method("GET", "POST", "UPDATE", "DELETE")
                            .filters(f -> f.filter(filter))
                            .uri("http://management-user-service:8082/"))
                    .route(r -> r.path("/api/v1/auth/login")
                            .and().method("GET", "POST", "UPDATE", "DELETE")
                            .filters(f -> f.filter(filter))
                            .uri("http://auth-service:8081/"))
                    .route(r -> r.path("/api/v1/**")
                            .and().method("GET", "POST", "UPDATE", "DELETE")
                            .filters(f -> f.filter(filter))
                            .uri("http://item-service:8083/"))
                    .route(r -> r.path("/v1/api/**")
                            .and().method("GET", "POST", "UPDATE", "DELETE")
                            .filters(f -> f.filter(filter))
                            .uri("http://request-service:8084/"))
                    .build();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new UnauthorizationException("Authorization failed");
        }
    }
}
