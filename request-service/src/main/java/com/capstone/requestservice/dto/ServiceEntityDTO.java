package com.capstone.requestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntityDTO {
    private Long id;

    private String uuid;

    private String name;

    private String created_by;

    private Timestamp created_on;

    private String lastupatedby;

    private Timestamp lastupdatedon;
}
