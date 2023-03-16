package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.DepartmentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    DepartmentEntity getDepartmentById(Long departmentid);

    List<DepartmentEntity> getAllDept();

    DepartmentEntity getDeptByUuid(String uuid);

    DepartmentEntity getAllByCode(String code);
}
