package com.capstone.approvalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApprovalDTO {
    private Long approvalid;
    private String modulename;
}
