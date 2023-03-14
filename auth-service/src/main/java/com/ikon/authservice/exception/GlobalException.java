package com.ikon.authservice.exception;

import com.ikon.authservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDTO<Object>> globalException(Exception e) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Bad Request")
                .data(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<ResponseDTO<Object>> invalidPasswordException(Exception e) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Password is invalid!")
                .data(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> globalException(UserNotFoundException e) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message("Bad Request")
                .data(e.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }
}


