package com.capstone.approvalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddApprovalDTO {
    private String requestuuid;
    private String modulename;
    private String explain;
}
