package com.capstonebackend.managementuserservice.service;


import com.capstonebackend.managementuserservice.dto.UserDTO;
import org.springframework.security.core.Authentication;

public interface TokenService {
    String generatedToken(Authentication authentication);
    UserDTO decodeToken(String token);
//    void addUser(UserDTO userDTO);
}

