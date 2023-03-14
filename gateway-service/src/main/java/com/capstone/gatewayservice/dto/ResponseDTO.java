package com.capstone.gatewayservice.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ResponseDTO {
    private HttpStatus httpStatus;
    private String message;
}

