package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import com.capstonebackend.managementuserservice.repository.RoleRepository;
import com.capstonebackend.managementuserservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleEntity getRoleById(Long roleid) {
        return roleRepository.findById(roleid).get();
    }

    @Override
    public List<RoleEntity> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity getRoleByUuid(String uuid) {
        return roleRepository.findByUuid(uuid);
    }
}
