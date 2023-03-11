package com.ikon.authservice.controller;


import com.ikon.authservice.dto.ResponseDTO;
import com.ikon.authservice.dto.UserDTO;
import com.ikon.authservice.dto.UserInfoDTO;
import com.ikon.authservice.dto.UserResponseDTO;
import com.ikon.authservice.entity.User;
import com.ikon.authservice.service.TokenService;

import com.ikon.authservice.service.impl.JpaUserDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> token(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = tokenService.generatedToken(authentication);
        return new ResponseEntity(ResponseDTO.builder()
                                    .httpStatus(HttpStatus.OK)
                                    .message("token created")
                                    .data(token)
                                    .build(), HttpStatus.OK);

    }

    @GetMapping("/user-data")
    public ResponseEntity<UserDTO> userInfo(@RequestHeader(name = "Authorization") String tokenBearer) {
        UserDTO user = tokenService.decodeToken(tokenBearer);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/user-data/username-dept")
    public ResponseEntity<UserInfoDTO> usernameDeptInfo(@RequestHeader(name = "Authorization") String tokenBearer) {
        UserDTO user = tokenService.decodeToken(tokenBearer);

        return new ResponseEntity(new UserInfoDTO(user.getUsername(), user.getDepartments().get(0).getUuid()), HttpStatus.OK);
    }
}
