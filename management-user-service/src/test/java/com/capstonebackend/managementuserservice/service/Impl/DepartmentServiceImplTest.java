package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.DepartmentEntity;
import com.capstonebackend.managementuserservice.repository.DepartmentRepository;
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
public class DepartmentServiceImplTest {
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    protected DepartmentRepository departmentRepository;

    @Test
    public void getDepartmentById(){
        DepartmentEntity department = new DepartmentEntity(1L, "uuuu", "ccc", "dddd", true, "sys", null, "sys", null);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        DepartmentEntity actual = departmentService.getDepartmentById(1L);
        Assertions.assertEquals(department, actual);
    }

    @Test public void getAllDept(){
        DepartmentEntity department = new DepartmentEntity(1L, "uuuu", "ccc", "dddd", true, "sys", null, "sys", null);

        when(departmentRepository.findAll()).thenReturn(List.of(department));

        List<DepartmentEntity> actual = departmentService.getAllDept();
        Assertions.assertEquals(department, actual.get(0));
    }

    @Test
    public void getDeptByUuid(){
        DepartmentEntity department = new DepartmentEntity(1L, "uuuu", "ccc", "dddd", true, "sys", null, "sys", null);

        when(departmentRepository.findByUuid("uuuu")).thenReturn(department);

        DepartmentEntity actual = departmentService.getDeptByUuid("uuuu");
        Assertions.assertEquals(department, actual);
    }

    @Test
    public void getDeptByCode(){
        DepartmentEntity department = new DepartmentEntity(1L, "uuuu", "ccc", "dddd", true, "sys", null, "sys", null);

        when(departmentRepository.findByCode("ccc")).thenReturn(department);

        DepartmentEntity actual = departmentService.getAllByCode("ccc");
        Assertions.assertEquals(department, actual);
    }
}
