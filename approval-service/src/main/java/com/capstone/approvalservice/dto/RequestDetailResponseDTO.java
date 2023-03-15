package com.capstone.approvalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String createdon;
    private String lastupdatedby;
    private String lastupdatedon;
}
