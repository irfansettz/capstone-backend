package com.ikon.gatewayservice.config;

import com.ikon.gatewayservice.filter.AdminFilter;
import com.ikon.gatewayservice.filter.AppFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {
    private final AppFilter filter;
    private final AdminFilter adminFilter;

    private final String endPoint8081 = "http://localhost:8081/";
    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route(r -> r.path("/v1/api/users/**")
                       .and().method("GET","POST", "PUT", "DELETE")
                        .filters(f -> f.filter(authenticationFilter()))
                        .uri("http://localhost:8082/"))
                .route(r -> r.path("/api/v1/auth/**")
                        .uri("http://localhost:8082/"))
//
//                .route(r -> r.path("/api/v1/auth/all-users")
//                        .filters(f -> f.filter(filter).filter(adminFilter))
//                        .uri("http://auth-service:8081/"))
//                .route(r -> r.path("/api/v1/auth/**")
//                        .uri("http://auth-service:8081/"))
                .build();
    }


    @Bean
    GatewayFilter authenticationFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            URI uri = request.getURI();
            String path = uri.getPath();

            // Check if the user has already been authenticated
            if (exchange.getRequest().getHeaders().containsKey("Authorization")) {
                return chain.filter(exchange);
            }

            // Redirect the user to the authentication service if they haven't been authenticated
            if (!path.startsWith("/api/v1/auth/login")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set("Location", "/api/v1/auth/login");
                return response.setComplete();
            }

            // Allow access to the authentication service
            return chain.filter(exchange);
        };
    }
}
