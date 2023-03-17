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
import java.util.Map;
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
    private final PermissionRoleService permissionRoleService;
    private final PermissionService permissionService;
    private final ApprovalGroupService approvalGroupService;
    private final ApprovalModuleService approvalModuleService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getAllUser(@RequestParam(name = "username", required = false) String username, @RequestParam(name = "email", required = false) String email){
        List<UserDTO> userDTOS = new ArrayList<>();
        if(username != null) {
            UserEntity user = userServices.getUserByUsername(username);
            userDTOS.add(fnGetUserByUuid(user.getUuid()));
        } else if(email != null) {
            UserEntity user = userServices.getUserByEmail(email);
            userDTOS.add(fnGetUserByUuid(user.getUuid()));
        } else {
            List<UserEntity> allUser = userServices.getAllUser();
            for (UserEntity user: allUser) {
                userDTOS.add(fnGetUserByUuid(user.getUuid()));
            }
        }

        UserResponseDTO response = new UserResponseDTO(userDTOS);
        response.setCode(200);
        response.setStatus("success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
        UserDTO userDTO = new UserDTO(newUser.getUuid(), newUser.getUsername(), newUser.getEmail(), newUser.getPassword(), newUser.isActive(), newUser.getToken(), newUser.getCreatedby(), newUser.getCreatedon(), newUser.getLastupatedby(), newUser.getLastupdatedon(), listDepartments, listRoles);

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
            roleDTO.add(getRole(role));
        }

        // return response
        RoleResponseDTO response = new RoleResponseDTO();
        response.setCode(200);
        response.setStatus("success");
        response.setData(roleDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping ("/roles/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id){
        // get data
        RoleEntity role = roleService.getRoleById(id);
        RoleDTO roleDTO = getRole(role);

        // return response
        RoleResponseDTO response = new RoleResponseDTO(List.of(roleDTO));
        response.setCode(200);
        response.setStatus("success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/roles/uuid/{uuid}")
    public ResponseEntity<RoleResponseDTO> getRoleByUuid(@PathVariable String uuid){
        // get data
        RoleEntity role = roleService.getRoleByUuid(uuid);
        RoleDTO roleDTO = getRole(role);

        // return response
        RoleResponseDTO response = new RoleResponseDTO(List.of(roleDTO));
        response.setCode(200);
        response.setStatus("success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        // get data
        List<DepartmentEntity> allDept = departmentService.getAllDept();;

        List<DepartmentDTO> departmentDTO = new ArrayList<>();
        for (DepartmentEntity department : allDept) {
            departmentDTO.add(new DepartmentDTO(department.getId(),department.getUuid(), department.getCode(), department.getName()));
        }

        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id){
        // get data
        DepartmentEntity dept = departmentService.getDepartmentById(id);
        DepartmentDTO departmentDTO = new DepartmentDTO(dept.getId(),dept.getUuid(), dept.getCode(), dept.getName());

        // return response
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

    @GetMapping("/departments/uuid/{uuid}")
    public ResponseEntity<DepartmentDTO> getDepartmentByUuid(@PathVariable String uuid){
        // get data
        DepartmentEntity dept = departmentService.getDeptByUuid(uuid);
        DepartmentDTO departmentDTO = new DepartmentDTO(dept.getId(),dept.getUuid(), dept.getCode(), dept.getName());

        // return response
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

    @GetMapping("/departments/code/{code}")
    public ResponseEntity<DepartmentDTO> getDepartmentByCode(@PathVariable String code){
        // get data
        DepartmentEntity dept = departmentService.getAllByCode(code);
        DepartmentDTO departmentDTO = new DepartmentDTO(dept.getId(),dept.getUuid(), dept.getCode(), dept.getName());

        // return response
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
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

    @PostMapping("/departments")
    public ResponseEntity<UserResponseDTO> addUserDepartment(@RequestBody UserDepartmentDTO userDept){
        // get data
        UserEntity user = userServices.getUserByUuid(userDept.getUseruuid());
        DepartmentEntity department = departmentService.getDeptByUuid(userDept.getDepartmentuuid());

        // save data
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(user.getId(), department.getId());
        userDepartmentService.addUserDepartment(userDepartment);

        // make response
        UserDTO userDTO = fnGetUserByUuid(userDept.getUseruuid());

        // return response
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("created");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/roles")
    public ResponseEntity<UserResponseDTO> deleteUserRole(@RequestParam String useruuid, @RequestParam String roleuuid){
        // get data
        UserEntity user = userServices.getUserByUuid(useruuid);
        RoleEntity role = roleService.getRoleByUuid(roleuuid);
        UserRoleEntity userRole = userRoleService.getDataByUseridAndRoleid(user.getId(), role.getId());

        // delete data
        userRoleService.deleteById(userRole.getId());

        // make response
        UserDTO userDTO = fnGetUserByUuid(useruuid);

        // return response
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/departments")
    public ResponseEntity<UserResponseDTO> deleteUserDepartment(@RequestParam String useruuid, @RequestParam String departmentuuid){
        // get data
        UserEntity user = userServices.getUserByUuid(useruuid);
        DepartmentEntity department = departmentService.getDeptByUuid(departmentuuid);
        UserDepartmentEntity userDepartment = userDepartmentService.getDeptByUseridAndDeptid(user.getId(), department.getId());

        // delete user department
        userDepartmentService.deleteById(userDepartment.getId());

        // make response
        UserDTO userDTO = fnGetUserByUuid(useruuid);

        // return response
        UserResponseDTO response = new UserResponseDTO();
        response.setData(List.of(userDTO));
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/approval-module")
    public ResponseEntity<ApprovalModuleDTO> getApprovalModuleByName(@RequestParam String name){
        ApprovalModuleEntity approvalModule = approvalModuleService.getByName(name);

        ApprovalModuleDTO response = new ApprovalModuleDTO(approvalModule.getId(), approvalModule.getUuid(), approvalModule.getName(), approvalModule.getExplain());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/approval-module/{id}")
    public ResponseEntity<ApprovalModuleDTO> getApprovalModuleById(@PathVariable Long id){
        ApprovalModuleEntity approvalModule = approvalModuleService.getById(id);

        ApprovalModuleDTO response = new ApprovalModuleDTO(approvalModule.getId(), approvalModule.getUuid(), approvalModule.getName(), approvalModule.getExplain());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password/{uuid}")
    public ResponseEntity<ResponseDTO> resetPasswordByUuid(@RequestBody PasswordDTO password, @PathVariable String uuid){
        userServices.resetPasswordByUuid(uuid, passwordEncoder.encode(password.getPassword()));
        return new ResponseEntity<>(new ResponseDTO(201, "success", "Password Updated"), HttpStatus.CREATED);
    }

    public UserDTO fnGetUserByUuid(String uuid){
        UserEntity user = userServices.getUserByUuid(uuid);

        // get all roles user
        List<UserRoleEntity> allUserRole = userRoleService.getAllUserRolesByUserid(user.getId());
        List<RoleDTO> allRoleUser = new ArrayList<>();
        for (UserRoleEntity userRoles: allUserRole) {
            RoleEntity role = roleService.getRoleById(userRoles.getRoleid());
            RoleDTO roleDTO = getRole(role);
            allRoleUser.add(roleDTO);
        }

        // get all departments user
        List<UserDepartmentEntity> allDepartmentUser = userDepartmentService.getAllDepartmentByUserid(user.getId());
        List<DepartmentDTO> allDeptUser = new ArrayList<>();
        for (UserDepartmentEntity userDept: allDepartmentUser) {
            DepartmentEntity department = departmentService.getDepartmentById(userDept.getDepartmentid());
            DepartmentDTO departmentDTO = new DepartmentDTO(department.getId(),department.getUuid(), department.getCode(), department.getName());
            allDeptUser.add(departmentDTO);
        }

        // make response
        return new UserDTO(user.getUuid(), user.getUsername(), user.getEmail(), user.getPassword(), user.isActive(), user.getToken(), user.getCreatedby(), user.getCreatedon(), user.getLastupatedby(), user.getLastupdatedon(), allDeptUser, allRoleUser);
    }

    public RoleDTO getRole(RoleEntity role){
        // get permissions
        List<PermissionRoleEntity> permissionRoleEntities = permissionRoleService.getAllByRoleid(role.getId());
        List<PermissionDTO> permissions = new ArrayList<>();
        for (PermissionRoleEntity permissionRole: permissionRoleEntities) {
            PermissionEntity permission = permissionService.getById(permissionRole.getPermissionid());
            permissions.add(new PermissionDTO(permission.getUuid(), permission.getName(), permission.getSlug()));
        }

        //get approval module
        List<ApprovalGroupEntity> approvalGroupEntities = approvalGroupService.getAllByRoleid(role.getId());
        List<ApprovalModuleDTO> approvalModuleDTOS = new ArrayList<>();
        for (ApprovalGroupEntity approvalGroup: approvalGroupEntities) {
            ApprovalModuleEntity approvalModule = approvalModuleService.getById(approvalGroup.getModuleid());
            approvalModuleDTOS.add(new ApprovalModuleDTO(approvalModule.getId(),approvalModule.getUuid(), approvalModule.getName(), approvalModule.getExplain()));
        }

        return new RoleDTO(role.getUuid(), role.getName(), permissions, approvalModuleDTOS);
    }
}
