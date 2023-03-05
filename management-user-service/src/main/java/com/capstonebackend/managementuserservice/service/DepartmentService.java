package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.DepartmentEntity;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    DepartmentEntity getDepartmentById(Long departmentid);
}
