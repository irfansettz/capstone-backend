package com.capstonebackend.managementuserservice;

import com.capstonebackend.managementuserservice.config.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
@EnableConfigurationProperties(RSAKeyProperties.class)
public class ManagementUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementUserServiceApplication.class, args);
    }

}
