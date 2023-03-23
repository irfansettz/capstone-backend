package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.ApprovalModuleEntity;
import com.capstonebackend.managementuserservice.repository.ApprovalModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ApprovalModuleImplTest {
    @InjectMocks
    private ApprovalModuleImpl approvalModule;

    @Mock
    protected ApprovalModuleRepository approvalModuleRepository;

    @Test
    public void getById(){
        ApprovalModuleEntity approval = new ApprovalModuleEntity(1L, "uuuu", "mdl", "mdl exp", null, null, null, null);
        when(approvalModuleRepository.findById(1L)).thenReturn(Optional.of(approval));

        ApprovalModuleEntity actual = approvalModule.getById(1L);
        Assertions.assertEquals(approval, actual);
    }

    @Test
    public void getByUuid(){
        ApprovalModuleEntity approval = new ApprovalModuleEntity(1L, "uuuu", "mdl", "mdl exp", null, null, null, null);
        when(approvalModuleRepository.findByUuid("uuuu")).thenReturn(approval);

        ApprovalModuleEntity actual = approvalModule.getByUuid("uuuu");
        Assertions.assertEquals(approval, actual);
    }

    @Test
    public void getByName(){
        ApprovalModuleEntity approval = new ApprovalModuleEntity(1L, "uuuu", "mdl", "mdl exp", null, null, null, null);
        when(approvalModuleRepository.findByName("mdl")).thenReturn(approval);

        ApprovalModuleEntity actual = approvalModule.getByName("mdl");
        Assertions.assertEquals(approval, actual);
    }
}
