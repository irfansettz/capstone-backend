package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.PermissionEntity;
import com.capstonebackend.managementuserservice.repository.PermissionRepository;
import com.capstonebackend.managementuserservice.repository.PermissionRoleRepository;
import com.capstonebackend.managementuserservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public PermissionEntity getById(Long permissionid) {
        return permissionRepository.findById(permissionid).get();
    }
}
