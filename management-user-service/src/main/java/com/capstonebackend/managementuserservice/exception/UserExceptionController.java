package com.capstonebackend.managementuserservice.exception;

import com.capstonebackend.managementuserservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ResponseDTO> userNotFound(UserNotFound e){
        ResponseDTO response = new ResponseDTO(404, "failed", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIsAny.class)
    public ResponseEntity<ResponseDTO> userIsAny(UserIsAny e){
        ResponseDTO response = new ResponseDTO(400, "failed", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
