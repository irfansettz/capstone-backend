package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    RoleEntity getRoleById(Long roleid);
}
