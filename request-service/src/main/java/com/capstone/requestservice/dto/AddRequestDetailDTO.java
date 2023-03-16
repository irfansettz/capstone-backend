package com.capstone.requestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddRequestDetailDTO {
    private SaveRequestDTO header;
    private List<RequestDetailDTO> details;
}
