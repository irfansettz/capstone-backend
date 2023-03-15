package com.capstone.approvalservice.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseDTO<T> {
    private HttpStatus httpStatus;
    private String message;
    private T data;
}
