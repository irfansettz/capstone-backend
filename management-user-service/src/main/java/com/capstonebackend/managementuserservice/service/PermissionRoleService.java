package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.PermissionRoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionRoleService {
    List<PermissionRoleEntity> getAllByRoleid(Long roleid);
}
