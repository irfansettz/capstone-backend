package com.capstonebackend.itemservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceDTO {
    private Long id;
    private String uuid;
    private String name;
    private String created_by;
    private Timestamp created_on;
    private String lastupatedby;
    private Timestamp lastupdatedon;
}
