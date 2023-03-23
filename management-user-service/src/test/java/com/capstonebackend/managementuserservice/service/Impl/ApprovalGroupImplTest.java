package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.ApprovalGroupEntity;
import com.capstonebackend.managementuserservice.repository.ApprovalGroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApprovalGroupImplTest {
    @InjectMocks
    private ApprovalGroupImpl approvalGroupImpl;

    @Mock
    protected ApprovalGroupRepository approvalGroupRepository;

    @Test
    void getAllByRoleid(){
        ApprovalGroupEntity app1 = new ApprovalGroupEntity(1L, 1L, 1L);

        when(approvalGroupRepository.findAllByRoleid(1L)).thenReturn(List.of(app1));
        List<ApprovalGroupEntity> actual = approvalGroupImpl.getAllByRoleid(1L);
        Assertions.assertEquals(List.of(app1), actual);
    }
}
