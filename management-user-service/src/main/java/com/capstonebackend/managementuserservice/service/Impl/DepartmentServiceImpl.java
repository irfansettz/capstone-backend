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

    @Override
    public DepartmentEntity getDeptByUuid(String uuid) {
        return departmentRepository.findByUuid(uuid);
    }

    @Override
    public DepartmentEntity getAllByCode(String code) {
        return departmentRepository.findByCode(code);
    }
}
