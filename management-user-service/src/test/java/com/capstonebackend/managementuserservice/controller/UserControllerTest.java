package com.capstonebackend.managementuserservice.controller;

import com.capstonebackend.managementuserservice.dto.*;
import com.capstonebackend.managementuserservice.entity.*;
import com.capstonebackend.managementuserservice.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServices userServices;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private RoleService roleService;

    @Mock
    private UserDepartmentService userDepartmentService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private PermissionRoleService permissionRoleService;

    @Mock
    private PermissionService permissionService;

    @Mock
    private ApprovalGroupService approvalGroupService;

    @Mock
    private ApprovalModuleService approvalModuleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    private void setup(){
        mockMvc = standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUser() throws Exception {
        // create user entities and DTOs
        UserEntity usr1 = new UserEntity(1L, "aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);

        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(200);
        expectedResponse.setStatus("success");

        // mock userServices methods
        when(userServices.getAllUser()).thenReturn(List.of(usr1));
        when(userServices.getUserByUuid(usr1.getUuid())).thenReturn(usr1);
        when(userRoleService.getAllUserRolesByUserid(usr1.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(usr1.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void testGetAllUserByUserName() throws Exception {
        // create user entities and DTOs
        UserEntity usr1 = new UserEntity(1L, "aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);

        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(200);
        expectedResponse.setStatus("success");

        // mock userServices methods
        when(userServices.getUserByUsername("irfan")).thenReturn(usr1);
        when(userServices.getUserByUuid(usr1.getUuid())).thenReturn(usr1);
        when(userRoleService.getAllUserRolesByUserid(usr1.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(usr1.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users?username=irfan")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void testGetAllUserByEmail() throws Exception {
        // create user entities and DTOs
        UserEntity usr1 = new UserEntity(1L, "aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);

        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(200);
        expectedResponse.setStatus("success");

        // mock userServices methods
        when(userServices.getUserByEmail("irfan@gmail.com")).thenReturn(usr1);
        when(userServices.getUserByUuid(usr1.getUuid())).thenReturn(usr1);
        when(userRoleService.getAllUserRolesByUserid(usr1.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(usr1.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users?email=irfan@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void testAddUser() throws Exception {
        UserRequestDTO usr = new UserRequestDTO("irfan", "irfan@gmail.com", "12345678", true, "sys", "sys");
        // mock the passwordEncoder
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encrypted_password");

        UserEntity addUser = new UserEntity(1L, UUID.randomUUID().toString(), usr.getUsername(), usr.getEmail(), "PPPPP", true, null, usr.getCreatedby(), null, usr.getLastupdatedby(), null, null);
        UserEntity newUser = new UserEntity(1L, addUser.getUuid(), addUser.getUsername(), addUser.getEmail(), addUser.getPassword(), addUser.isActive(), addUser.getToken(), addUser.getCreatedby(), addUser.getCreatedon(), addUser.getLastupatedby(), addUser.getLastupdatedon(), addUser.getDatenonactive());

        lenient().when(userServices.addUser(addUser)).thenReturn(newUser);

        List<RoleDTO> roles = new ArrayList<>();
        List<DepartmentDTO> departments = new ArrayList<>();

        UserDTO userDTO = new UserDTO(newUser.getUuid(), newUser.getUsername(), newUser.getEmail(), newUser.getPassword(), newUser.isActive(), newUser.getToken(), newUser.getCreatedby(), newUser.getCreatedon(), newUser.getLastupatedby(), newUser.getLastupdatedon(), departments, roles);

        // create expected response
        UserResponseDTO expectedResponse = new UserResponseDTO();
        expectedResponse.setData(List.of(userDTO));
        expectedResponse.setCode(201);
        expectedResponse.setStatus("success");
        expectedResponse.setMessage("data created");

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usr))) // pass user object as JSON string in the request body
                .andExpect(MockMvcResultMatchers.status().isCreated()) // validate the response status code
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void getDataByUuid() throws Exception {
        // create user entities and DTOs
        UserEntity usr1 = new UserEntity(1L, "aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);

        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("aaaa", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(200);
        expectedResponse.setStatus("success");

        // mock userServices methods
        when(userServices.getUserByUuid("aaaa")).thenReturn(usr1);
        when(userServices.getUserByUuid(usr1.getUuid())).thenReturn(usr1);
        when(userRoleService.getAllUserRolesByUserid(usr1.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(usr1.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/aaaa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void getAllRole() throws Exception {
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);

        when(roleService.getAllRole()).thenReturn(List.of(role));
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        RoleResponseDTO response = new RoleResponseDTO();
        response.setCode(200);
        response.setStatus("success");
        response.setData(List.of(roleDTO));

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(response))); // validate the response body
    }

    @Test
    public void getRoleById() throws Exception {
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);

        when(roleService.getRoleById(1L)).thenReturn(role);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        RoleResponseDTO response = new RoleResponseDTO();
        response.setCode(200);
        response.setStatus("success");
        response.setData(List.of(roleDTO));

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/roles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(response))); // validate the response body
    }

    @Test
    public void getRoleByUid() throws Exception {
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);

        when(roleService.getRoleByUuid("rrrr")).thenReturn(role);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        RoleResponseDTO response = new RoleResponseDTO();
        response.setCode(200);
        response.setStatus("success");
        response.setData(List.of(roleDTO));

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/roles/uuid/rrrr")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(response))); // validate the response body
    }

    @Test
    public void getAllDepartments() throws Exception{
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");

        when(departmentService.getAllDept()).thenReturn(List.of(department));

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(new DepartmentDTO[]{dept}))); // validate the response body
    }

    @Test
    public void getDepartmentById() throws Exception{
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");

        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/departments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(dept))); // validate the response body
    }

    @Test
    public void getDepartmentByUuid() throws Exception{
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");

        when(departmentService.getDeptByUuid("dddd")).thenReturn(department);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/departments/uuid/dddd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(dept))); // validate the response body
    }

    @Test
    public void getDepartmentByCode() throws Exception{
        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");

        when(departmentService.getAllByCode("ccc")).thenReturn(department);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/departments/code/ccc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(dept))); // validate the response body
    }

    @Test
    public void addUserRole() throws Exception{
        UserRoleDTO userRoleDTO = new UserRoleDTO("uuuu", "rrrr");
        UserEntity user = new UserEntity(1L, "uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);

        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        UserRoleEntity userRoleEntity = new UserRoleEntity(user.getId(), role.getId());

        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(201);
        expectedResponse.setStatus("success");
        expectedResponse.setMessage("created");

        when(userServices.getUserByUuid(userRoleDTO.getUseruuid())).thenReturn(user);
        when(roleService.getRoleByUuid(userRoleDTO.getRoleuuid())).thenReturn(role);
        doNothing().when(userRoleService).addData(userRoleEntity);

        when(userServices.getUserByUuid(user.getUuid())).thenReturn(user);
        when(userRoleService.getAllUserRolesByUserid(user.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(user.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/users/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRoleDTO))) // pass user object as JSON string in the request body
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void addUserDepartment() throws Exception{
        UserDepartmentDTO userDepartmentDTO = new UserDepartmentDTO("uuuu", "dddd");
        UserEntity user = new UserEntity(1L, "uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);

        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        UserDepartmentEntity userDepartment1 = new UserDepartmentEntity(user.getId(), department.getId());

        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(201);
        expectedResponse.setStatus("success");
        expectedResponse.setMessage("created");

        when(userServices.getUserByUuid(userDepartmentDTO.getUseruuid())).thenReturn(user);
        when(departmentService.getDeptByUuid(userDepartmentDTO.getDepartmentuuid())).thenReturn(department);
        doNothing().when(userDepartmentService).addUserDepartment(userDepartment1);

        when(userServices.getUserByUuid(user.getUuid())).thenReturn(user);
        when(userRoleService.getAllUserRolesByUserid(user.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(user.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/users/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDepartmentDTO))) // pass user object as JSON string in the request body
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void deleteUserRole() throws Exception{
        UserRoleDTO userRoleDTO = new UserRoleDTO("uuuu", "rrrr");
        UserEntity user = new UserEntity(1L, "uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);

        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        UserRoleEntity userRoleEntity = new UserRoleEntity(user.getId(), role.getId());

        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(201);
        expectedResponse.setStatus("success");
        expectedResponse.setMessage("deleted");

        when(userServices.getUserByUuid(userRoleDTO.getUseruuid())).thenReturn(user);
        when(roleService.getRoleByUuid(userRoleDTO.getRoleuuid())).thenReturn(role);
        when(userRoleService.getDataByUseridAndRoleid(user.getId(), role.getId())).thenReturn(userRoleEntity);
        doNothing().when(userRoleService).deleteById(userRoleEntity.getId());

        when(userServices.getUserByUuid(user.getUuid())).thenReturn(user);
        when(userRoleService.getAllUserRolesByUserid(user.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(user.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/users/roles?useruuid=uuuu&roleuuid=rrrr")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void deleteUserDepartment() throws Exception{
        UserDepartmentDTO userDepartmentDTO = new UserDepartmentDTO("uuuu", "dddd");
        UserEntity user = new UserEntity(1L, "uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, null);
        RoleEntity role = new RoleEntity(1L, "rrrr", "role", "sys", null, "sys", null);
        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);
        ApprovalGroupEntity approvalGroupEntity = new ApprovalGroupEntity(1L, 1L, 1L);

        DepartmentEntity department = new DepartmentEntity(1L, "dddd", "ccc", "dept", true, "sys", null, "sys", null);
        PermissionEntity permission = new PermissionEntity(1L, "pppp", "perm", "perm.perm", "1.0", true, "sys", null, "sys", null);
        ApprovalModuleEntity approvalModuleEntity = new ApprovalModuleEntity(1L, "appp", "module", "module exp", "sys", null, "sys", null);
        DepartmentDTO dept = new DepartmentDTO(1L, "dddd", "ccc", "dept");
        PermissionDTO permissionDTO = new PermissionDTO("pppp", "perm", "perm.perm");
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(1L, "appp", "module", "module exp");
        RoleDTO roleDTO = new RoleDTO("rrrr", "role", List.of(permissionDTO), List.of(approvalModuleDTO));
        UserDTO user1 = new UserDTO("uuuu", "irfan", "irfan@gmail.com", "fdsfdsfs", true, null, "sys", null, "sys", null, List.of(dept), List.of(roleDTO));
        UserDepartmentEntity userDepartment1 = new UserDepartmentEntity(user.getId(), department.getId());

        // create expected response
        List<UserDTO> allUser = List.of(user1);
        UserResponseDTO expectedResponse = new UserResponseDTO(allUser);
        expectedResponse.setCode(201);
        expectedResponse.setStatus("success");
        expectedResponse.setMessage("deleted");

        when(userServices.getUserByUuid(userDepartmentDTO.getUseruuid())).thenReturn(user);
        when(departmentService.getDeptByUuid(userDepartmentDTO.getDepartmentuuid())).thenReturn(department);
        when(userDepartmentService.getDeptByUseridAndDeptid(user.getId(), department.getId())).thenReturn(userDepartment1);
        doNothing().when(userDepartmentService).deleteById(userDepartment1.getId());

        when(userServices.getUserByUuid(user.getUuid())).thenReturn(user);
        when(userRoleService.getAllUserRolesByUserid(user.getId())).thenReturn(List.of(userRole));
        when(roleService.getRoleById(userRole.getRoleid())).thenReturn(role);
        when(userDepartmentService.getAllDepartmentByUserid(user.getId())).thenReturn(List.of(userDepartment));
        when(departmentService.getDepartmentById(userDepartment.getDepartmentid())).thenReturn(department);
        when(permissionRoleService.getAllByRoleid(role.getId())).thenReturn(List.of(permissionRoleEntity));
        when(permissionService.getById(permissionRoleEntity.getPermissionid())).thenReturn(permission);
        when(approvalGroupService.getAllByRoleid(role.getId())).thenReturn(List.of(approvalGroupEntity));
        when(approvalModuleService.getById(approvalGroupEntity.getModuleid())).thenReturn(approvalModuleEntity);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/users/departments?useruuid=uuuu&departmentuuid=dddd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse))); // validate the response body
    }

    @Test
    public void addApprovalModuleByName() throws Exception{
        ApprovalModuleEntity approvalModule = new ApprovalModuleEntity(1L, "uuuu", "mdl", "mdl exp", null, null, null, null);
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(approvalModule.getId(), approvalModule.getUuid(), approvalModule.getName(), approvalModule.getExplain());

        when(approvalModuleService.getByName("mdl")).thenReturn(approvalModule);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/approval-module?name=mdl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(approvalModuleDTO))); // validate the response body
    }

    @Test
    public void addApprovalModuleById() throws Exception{
        ApprovalModuleEntity approvalModule = new ApprovalModuleEntity(1L, "uuuu", "mdl", "mdl exp", null, null, null, null);
        ApprovalModuleDTO approvalModuleDTO = new ApprovalModuleDTO(approvalModule.getId(), approvalModule.getUuid(), approvalModule.getName(), approvalModule.getExplain());

        when(approvalModuleService.getById(1L)).thenReturn(approvalModule);

        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/approval-module/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(approvalModuleDTO))); // validate the response body
    }

    @Test
    public void resetPassword() throws Exception{
        PasswordDTO passwordDTO = new PasswordDTO("password");
        String uuid = "uuuu";
        // mock the passwordEncoder
        lenient().when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encrypted_password");

        lenient().doNothing().when(userServices).resetPasswordByUuid(uuid, "ppppp");

        ResponseDTO response = new ResponseDTO(201, "success", "Password Updated");
        // make request and validate response
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/users/reset-password/uuuu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(response))); // validate the response body
    }
}

