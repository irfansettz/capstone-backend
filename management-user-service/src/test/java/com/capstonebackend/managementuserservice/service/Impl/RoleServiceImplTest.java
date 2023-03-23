package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import com.capstonebackend.managementuserservice.repository.RoleRepository;
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
public class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    protected RoleRepository roleRepository;

    @Test
    public void getRoleById(){
        RoleEntity role = new RoleEntity(1L, "uuuu", "role", "sys", null, "sys", null);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        RoleEntity actual = roleService.getRoleById(1L);
        Assertions.assertEquals(role, actual);
    }

    @Test
    public void getAllRole(){
        RoleEntity role = new RoleEntity(1L, "uuuu", "role", "sys", null, "sys", null);

        when(roleRepository.findAll()).thenReturn(List.of(role));

        List<RoleEntity> actual = roleService.getAllRole();
        Assertions.assertEquals(List.of(role), actual);
    }

    @Test
    public void getRoleByUuid(){
        RoleEntity role = new RoleEntity(1L, "uuuu", "role", "sys", null, "sys", null);

        when(roleRepository.findByUuid("uuuu")).thenReturn(role);

        RoleEntity actual = roleService.getRoleByUuid("uuuu");
        Assertions.assertEquals(role, actual);
    }
}
