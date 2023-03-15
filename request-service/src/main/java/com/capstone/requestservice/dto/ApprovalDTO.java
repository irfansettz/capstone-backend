package com.capstone.requestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDTO {
    private Long id;
    private String uuid;
    private ApprovalModuleDTO module;
    private DepartmentInfoDTO department;
    private String description;
    private String status;
    private String createdby;
    private String createdon;
    private String lastupdatedby;
    private String lastupdatedon;
}
