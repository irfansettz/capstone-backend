package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    RoleEntity getRoleById(Long roleid);

    List<RoleEntity> getAllRole();

    RoleEntity getRoleByUuid(String uuid);
}
