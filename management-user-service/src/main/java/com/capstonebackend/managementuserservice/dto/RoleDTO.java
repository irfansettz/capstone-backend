package com.capstonebackend.managementuserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private String uuid;
    private String name;
    private List<PermissionDTO> permissions;
    private List<ApprovalModuleDTO> approvalModules;
}
