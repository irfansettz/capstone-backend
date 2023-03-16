package com.capstone.approvalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListDptDTO {
    List<DepartmentInfoDTO> depts;
}
