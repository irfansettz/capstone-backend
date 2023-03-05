package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import com.capstonebackend.managementuserservice.repository.RoleRepository;
import com.capstonebackend.managementuserservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleEntity getRoleById(Long roleid) {
        return roleRepository.findById(roleid).get();
    }
}
