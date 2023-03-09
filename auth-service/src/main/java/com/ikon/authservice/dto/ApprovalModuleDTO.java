package com.ikon.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalModuleDTO {
    private String uuid;
    private String name;
    private String explain;
}
