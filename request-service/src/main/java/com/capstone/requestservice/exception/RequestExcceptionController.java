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
        return new ResponseEntity<>(new ResponseDTO(404, "failed", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestNotFound.class)
    public ResponseEntity<ResponseDTO> requestNotFound(RequestNotFound e){
        return new ResponseEntity<>(new ResponseDTO(404, "failed", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestDetailNotFound.class)
    public ResponseEntity<ResponseDTO> requestDetailNotFound(RequestDetailNotFound e){
        return new ResponseEntity<>(new ResponseDTO(404, "failed", e.getMessage()), HttpStatus.OK);
    }
}
