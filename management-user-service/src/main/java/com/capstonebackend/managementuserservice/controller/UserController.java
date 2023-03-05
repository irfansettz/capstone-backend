package com.capstonebackend.managementuserservice.controller;

import com.capstonebackend.managementuserservice.dto.*;
import com.capstonebackend.managementuserservice.entity.UserEntity;
import com.capstonebackend.managementuserservice.service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserRequestDTO user){
        UserEntity addUser = new UserEntity();
        addUser.setUuid(UUID.randomUUID().toString());
        addUser.setUsername(user.getUsername());
        addUser.setEmail(user.getEmail());
        addUser.setPassword(passwordEncoder.encode(user.getPassword()));
        addUser.setActive(user.getActive());
        addUser.setCreatedby(user.getCreatedby());
        addUser.setLastupatedby(user.getLastupdatedby());

        UserEntity newUser = userServices.addUser(addUser);

        List<UserRoleDTO> listRoles = new ArrayList<>();
        List<UserDepartmentDTO> listDepartments = new ArrayList<>();

        UserDTO userDTO = new UserDTO(newUser.getUuid(), newUser.getUsername(), newUser.getEmail(), newUser.isActive(), newUser.getToken(), newUser.getCreatedby(), newUser.getCreatedon(), newUser.getLastupatedby(), newUser.getLastupdatedon(), listDepartments, listRoles);
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("data created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    
}
