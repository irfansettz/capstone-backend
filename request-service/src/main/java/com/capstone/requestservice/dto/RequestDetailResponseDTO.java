package com.capstone.requestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailResponseDTO {
    private String uuid;
    private ItemDTO item;
    private ServiceDTO service;
    private Integer qty;
    private double price;
    private String desc;
    private String createdby;
    private Timestamp createdon;
    private String lastupdatedby;
    private Timestamp lastupdatedon;
}
