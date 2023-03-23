package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.PermissionRoleEntity;
import com.capstonebackend.managementuserservice.repository.PermissionRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PermissionRoleImplTest {
    @InjectMocks
    private PermissionRoleImpl permissionRole;

    @Mock
    protected PermissionRoleRepository permissionRoleRepository;

    @Test
    public void getAllRoleById(){
        PermissionRoleEntity permissionRoleEntity = new PermissionRoleEntity(1L, 1L, 1L);

        when(permissionRoleRepository.findAllByRoleid(1L)).thenReturn(List.of(permissionRoleEntity));

        List<PermissionRoleEntity> actual = permissionRole.getAllByRoleid(1L);
        Assertions.assertEquals(List.of(permissionRoleEntity), actual);
    }
}
