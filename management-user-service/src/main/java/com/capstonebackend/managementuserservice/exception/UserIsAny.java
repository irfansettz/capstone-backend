package com.capstonebackend.managementuserservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIsAny extends RuntimeException{
    private String message;
}
