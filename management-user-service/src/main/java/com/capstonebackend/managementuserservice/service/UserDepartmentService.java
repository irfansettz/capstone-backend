package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.UserDepartmentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDepartmentService {
    List<UserDepartmentEntity> getAllDepartmentByUserid(long id);
}
