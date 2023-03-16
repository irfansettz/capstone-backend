package com.capstone.requestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailDTO {
    private String itemUuid;
    private String serviceUuid;
    private Integer qty;
    private double price;
    private String desc;
}
