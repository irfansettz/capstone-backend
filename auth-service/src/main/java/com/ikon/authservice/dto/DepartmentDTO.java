package com.ikon.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DepartmentDTO {
    private String uuid;
    private String code;
    private String name;
}
