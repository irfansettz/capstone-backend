package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.PermissionEntity;
import org.springframework.stereotype.Service;

@Service
public interface PermissionService {
    PermissionEntity getById(Long permissionid);
}
