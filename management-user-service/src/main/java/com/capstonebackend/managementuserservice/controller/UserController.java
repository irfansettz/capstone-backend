package com.capstonebackend.managementuserservice.controller;

import com.capstonebackend.managementuserservice.dto.*;
import com.capstonebackend.managementuserservice.entity.*;
import com.capstonebackend.managementuserservice.service.*;
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
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final UserDepartmentService userDepartmentService;
    private final DepartmentService departmentService;
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserRequestDTO user){
        //set up data to save
        UserEntity addUser = new UserEntity();
        addUser.setUuid(UUID.randomUUID().toString());
        addUser.setUsername(user.getUsername());
        addUser.setEmail(user.getEmail());
        addUser.setPassword(passwordEncoder.encode(user.getPassword()));
        addUser.setActive(user.getActive());
        addUser.setCreatedby(user.getCreatedby());
        addUser.setLastupatedby(user.getLastupdatedby());
        
        // save data
        UserEntity newUser = userServices.addUser(addUser);

        // make response
        List<RoleDTO> listRoles = new ArrayList<>();
        List<DepartmentDTO> listDepartments = new ArrayList<>();
        UserDTO userDTO = new UserDTO(newUser.getUuid(), newUser.getUsername(), newUser.getEmail(), newUser.isActive(), newUser.getToken(), newUser.getCreatedby(), newUser.getCreatedon(), newUser.getLastupatedby(), newUser.getLastupdatedon(), listDepartments, listRoles);

        // return response
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("data created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDTO> getUserByUuid(@PathVariable String uuid){
        UserEntity user = userServices.getUserByUuid(uuid);

        // get all roles user
        List<UserRoleEntity> allUserRole = userRoleService.getAllUserRolesByUserid(user.getId());
        List<RoleDTO> allRoleUser = new ArrayList<>();
        for (UserRoleEntity userRoles: allUserRole) {
            RoleEntity role = roleService.getRoleById(userRoles.getRoleid());
            RoleDTO roleDTO = new RoleDTO(role.getUuid(), role.getName());
            allRoleUser.add(roleDTO);
        }

        // get all departments user
        List<UserDepartmentEntity> allDepartmentUser = userDepartmentService.getAllDepartmentByUserid(user.getId());
        List<DepartmentDTO> allDeptUser = new ArrayList<>();
        for (UserDepartmentEntity userDept: allDepartmentUser) {
            DepartmentEntity department = departmentService.getDepartmentById(userDept.getDepartmentid());
            DepartmentDTO departmentDTO = new DepartmentDTO(department.getUuid(), department.getCode(), department.getName());
            allDeptUser.add(departmentDTO);
        }

        // make response
        UserDTO userDTO = new UserDTO(user.getUuid(), user.getUsername(), user.getEmail(), user.isActive(), user.getToken(), user.getCreatedby(), user.getCreatedon(), user.getLastupatedby(), user.getLastupdatedon(), allDeptUser, allRoleUser);

        // return response
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(200);
        response.setStatus("success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
