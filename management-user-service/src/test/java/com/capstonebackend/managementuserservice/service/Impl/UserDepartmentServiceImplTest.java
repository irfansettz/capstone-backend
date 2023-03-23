package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.UserDepartmentEntity;
import com.capstonebackend.managementuserservice.repository.UserDepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDepartmentServiceImplTest {
    @InjectMocks
    private UserDepartmentServiceImpl userDepartmentService;

    @Mock
    protected UserDepartmentRepository userDepartmentRepository;

    @Test
    public void getAllByUserid(){
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);

        when(userDepartmentRepository.findAllByUserid(1L)).thenReturn(List.of(userDepartment));

        List<UserDepartmentEntity> actual = userDepartmentService.getAllDepartmentByUserid(1L);
        Assertions.assertEquals(List.of(userDepartment), actual);
    }

    @Test
    public void addUserDepartment(){
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);

        userDepartmentService.addUserDepartment(userDepartment);
        verify(userDepartmentRepository).save(userDepartment);
    }

    @Test
    public void getDeptByUseridAndDeptid(){
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);

        when(userDepartmentRepository.findByUseridAndDepartmentid(1L, 1L)).thenReturn(userDepartment);

        UserDepartmentEntity actual = userDepartmentService.getDeptByUseridAndDeptid(1L, 1L);
        Assertions.assertEquals(userDepartment, actual);
    }

    @Test
    public void deleteById(){
        UserDepartmentEntity userDepartment = new UserDepartmentEntity(1L, 1L);

        userDepartmentService.deleteById(1L);
        verify(userDepartmentRepository).deleteById(1L);
    }
}
