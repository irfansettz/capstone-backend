package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.UserRoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRoleService {
    List<UserRoleEntity> getAllUserRolesByUserid(Long id);
}
