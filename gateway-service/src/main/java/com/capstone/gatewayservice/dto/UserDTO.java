package com.capstone.gatewayservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserDTO {
    private String uuid;
    private String username;
    private String email;
    private String password;
    private boolean active;
    private String token;
    private String createdby;
    private java.sql.Timestamp createdon;
    private String lastupdatedby;
    private java.sql.Timestamp lastupdatedon;
    private List<DepartmentDTO> departments;
    private List<RoleDTO> roles;
}

