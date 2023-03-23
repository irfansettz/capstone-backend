package com.capstonebackend.managementuserservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableWebMvc
//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        String securityName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("Procurement - API Documentation")
                        .description("API Documentation for Management User Service").version("1.0"))
                // Add security scheme to the OpenAPI specification to enable the Swagger UI to
                // send the Authorization header
                .addSecurityItem(new SecurityRequirement().addList(securityName))
                .components(new Components().addSecuritySchemes(securityName, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")));

}
