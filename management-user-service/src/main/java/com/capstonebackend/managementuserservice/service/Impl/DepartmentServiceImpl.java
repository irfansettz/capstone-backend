package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.DepartmentEntity;
import com.capstonebackend.managementuserservice.repository.DepartmentRepository;
import com.capstonebackend.managementuserservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentEntity getDepartmentById(Long departmentid) {
        return departmentRepository.findById(departmentid).get();
    }

    @Override
    public List<DepartmentEntity> getAllDept() {
        return departmentRepository.findAll();
    }
}
