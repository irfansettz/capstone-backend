package com.capstonebackend.managementuserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String uuid;
    private String username;
    private String email;
    private boolean active;
    private String token;
    private String createdby;
    private java.sql.Timestamp createdon;
    private String lastupdatedby;
    private Timestamp lastupdatedon;
    private List<UserDepartmentDTO> departments;
    private List<UserRoleDTO> roles;
}
