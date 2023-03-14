package com.capstone.gatewayservice.filter;

import com.capstone.gatewayservice.dto.UserDTO;
import com.capstone.gatewayservice.exception.UnauthorizationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppFilter implements GatewayFilter {
    private final RestTemplate restTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpRequest request =  exchange.getRequest();
            final List<String> apiEndpoints = List.of("/api/v1/auth/login");

            Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));
            if (isApiSecured.test(request)) {
                System.out.println("test");
                if (!request.getHeaders().containsKey("Authorization")) {
                    System.out.println("test2");
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
                System.out.println("test3");
                final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
                String newToken = token.split(" ")[1];
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(newToken);

                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<UserDTO> response = restTemplate.exchange("http://auth-service:8081/api/v1/auth/user-data", HttpMethod.GET, entity, UserDTO.class);
                System.out.println("test4");
                UserDTO userData = response.getBody();
                System.out.println("test5");
                exchange.getRequest().mutate().header("role", String.valueOf(Objects.requireNonNull(userData.getRoles().get(0).getName()))).build();
            }
            return chain.filter(exchange);
        } catch (UnauthorizationException e){
            throw new UnauthorizationException("Authorization failed");
        }

    }
}
