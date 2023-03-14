package com.capstone.gatewayservice.exception;

import com.capstone.gatewayservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationHandler {
    @ExceptionHandler(AuthorizationFailed.class)
    public ResponseEntity<ResponseDTO> authorizationFailed(AuthorizationFailed e){
        return new ResponseEntity<>(new ResponseDTO(HttpStatus.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
