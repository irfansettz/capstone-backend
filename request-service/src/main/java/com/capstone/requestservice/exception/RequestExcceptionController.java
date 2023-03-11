package com.capstone.requestservice.exception;

import com.capstone.requestservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestExcceptionController {
    @ExceptionHandler(RequestTypeNotFound.class)
    public ResponseEntity<ResponseDTO> requestTypeNotFound(RequestTypeNotFound e){
        return new ResponseEntity<>(new ResponseDTO(404, e.getStatus(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
