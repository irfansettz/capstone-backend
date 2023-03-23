package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.PermissionEntity;
import com.capstonebackend.managementuserservice.repository.PermissionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceImplTest {
    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Mock
    protected PermissionRepository permissionRepository;

    @Test
    public void getById(){
        PermissionEntity permission = new PermissionEntity(1L, "uuuu", "perm", "perm.perm", "1.0", true, null, null, null, null);

        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        PermissionEntity actual = permissionService.getById(1L);
        Assertions.assertEquals(permission, actual);
    }
}
