package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.UserDepartmentEntity;
import com.capstonebackend.managementuserservice.repository.UserDepartmentRepository;
import com.capstonebackend.managementuserservice.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDepartmentServiceImpl implements UserDepartmentService {
    private final UserDepartmentRepository userDepartmentRepository;

    @Override
    public List<UserDepartmentEntity> getAllDepartmentByUserid(long id) {
        return userDepartmentRepository.findAllByUserid(id);
    }

    @Override
    public void addUserDepartment(UserDepartmentEntity userDepartment) {
        userDepartmentRepository.save(userDepartment);
    }

    @Override
    public UserDepartmentEntity getDeptByUseridAndDeptid(long userid, Long departmentid) {
        return userDepartmentRepository.findByUseridAndDepartmentid(userid, departmentid);
    }

    @Override
    public void deleteById(Long id) {
        userDepartmentRepository.deleteById(id);
    }
}
