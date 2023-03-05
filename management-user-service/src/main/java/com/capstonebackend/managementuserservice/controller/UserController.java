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
        // make response
        UserDTO userDTO = fnGetUserByUuid(uuid);

        // return response
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(200);
        response.setStatus("success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<RoleResponseDTO> getAllRoles(){
        // get data
        List<RoleEntity> allRole = roleService.getAllRole();
        List<RoleDTO> roleDTO = new ArrayList<>();
        for (RoleEntity role: allRole) {
            roleDTO.add(new RoleDTO(role.getUuid(), role.getName()));
        }

        // return response
        RoleResponseDTO response = new RoleResponseDTO();
        response.setCode(200);
        response.setStatus("success");
        response.setData(roleDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/roles/{uuid}")
    public ResponseEntity<RoleResponseDTO> getRoleByUuid(@PathVariable String uuid){
        // get data
        RoleEntity role = roleService.getRoleByUuid(uuid);
        RoleDTO roleDTO = new RoleDTO(role.getUuid(), role.getName());

        // return response
        RoleResponseDTO response = new RoleResponseDTO(List.of(roleDTO));
        response.setCode(200);
        response.setStatus("success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/departments")
    public ResponseEntity<DepartmentResponseDTO> getAllDepartments(){
        // get data
        List<DepartmentEntity> allDept = departmentService.getAllDept();
        List<DepartmentDTO> departmentDTO = new ArrayList<>();
        for (DepartmentEntity department : allDept) {
            departmentDTO.add(new DepartmentDTO(department.getUuid(), department.getCode(), department.getName()));
        }

        // return response
        DepartmentResponseDTO response = new DepartmentResponseDTO(departmentDTO);
        response.setCode(200);
        response.setStatus("success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/departments/{uuid}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByUuid(@PathVariable String uuid){
        // get data
        DepartmentEntity dept = departmentService.getDeptByUuid(uuid);
        DepartmentDTO departmentDTO = new DepartmentDTO(dept.getUuid(), dept.getCode(), dept.getName());

        // return response
        DepartmentResponseDTO response = new DepartmentResponseDTO(List.of(departmentDTO));
        response.setCode(200);
        response.setStatus("success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<UserResponseDTO> addUserRole(@RequestBody UserRoleDTO userRoleDTO){
        // get data
        UserEntity user = userServices.getUserByUuid(userRoleDTO.getUseruuid());
        RoleEntity role = roleService.getRoleByUuid(userRoleDTO.getRoleuuid());

        // save data
        UserRoleEntity userRole = new UserRoleEntity(user.getId(), role.getId());
        userRoleService.addData(userRole);

        // make response
        UserDTO userDTO = fnGetUserByUuid(userRoleDTO.getUseruuid());

        // return response
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("created");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public UserDTO fnGetUserByUuid(String uuid){
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
        return new UserDTO(user.getUuid(), user.getUsername(), user.getEmail(), user.isActive(), user.getToken(), user.getCreatedby(), user.getCreatedon(), user.getLastupatedby(), user.getLastupdatedon(), allDeptUser, allRoleUser);
    }
}
