package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.UserRoleEntity;
import com.capstonebackend.managementuserservice.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceImplTest {
    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @Mock
    protected UserRoleRepository userRoleRepository;

    @Test
    public void getAllUserRolesByUserid(){
        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);

        when(userRoleRepository.findAllByUserid(1L)).thenReturn(List.of(userRole));

        List<UserRoleEntity> actual = userRoleService.getAllUserRolesByUserid(1L);
        Assertions.assertEquals(List.of(userRole), actual);
    }

    @Test
    public void addData(){
        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);

        userRoleService.addData(userRole);
        verify(userRoleRepository).save(userRole);
    }

    @Test
    public void getDataByUseridAndRoleid(){
        UserRoleEntity userRole = new UserRoleEntity(1L, 1L);

        when(userRoleRepository.findByUseridAndRoleid(1L,1L)).thenReturn(userRole);

        UserRoleEntity actual = userRoleService.getDataByUseridAndRoleid(1L, 1L);
        Assertions.assertEquals(userRole, actual);
    }

    @Test
    public void deleteById(){
        userRoleService.deleteById(1L);
        verify(userRoleRepository).deleteById(1L);
    }
}
