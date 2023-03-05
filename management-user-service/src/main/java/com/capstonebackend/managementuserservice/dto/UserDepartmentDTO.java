package com.capstonebackend.managementuserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDepartmentDTO {
    private String useruuid;
    private String departmentuuid;
}
