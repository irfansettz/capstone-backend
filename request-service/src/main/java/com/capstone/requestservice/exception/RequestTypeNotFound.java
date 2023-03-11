package com.capstone.requestservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTypeNotFound extends RuntimeException {
    private String status;
    private String message;
}
