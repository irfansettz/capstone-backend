package com.capstone.approvalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String uuid;
    private TypeDTO type;
    private DepartmentInfoDTO department;
    private String notrans;
    private String date;
    private String desc;
    private Long approvalDpt;
    private Long approvalFnc;
    private String createdby;
    private String createdon;
    private String lastupdatedby;
    private String lastupdatedon;
    private List<RequestDetailResponseDTO> details;
}
