package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.PermissionRoleEntity;
import com.capstonebackend.managementuserservice.repository.PermissionRoleRepository;
import com.capstonebackend.managementuserservice.service.PermissionRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionRoleImpl implements PermissionRoleService {
    private final PermissionRoleRepository permissionRoleRepository;

    @Override
    public List<PermissionRoleEntity> getAllByRoleid(Long roleid) {
        return permissionRoleRepository.findAllByRoleid(roleid);
    }
}
