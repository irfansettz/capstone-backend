package com.capstone.approvalservice.service;

import com.capstone.approvalservice.dto.StatusDTO;
import com.capstone.approvalservice.entity.ApprovalEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApprovalService {
    Long addApproval(ApprovalEntity approvalSave);

    void updateStatusByUuid(String uuid, String username, StatusDTO status);

    ApprovalEntity getByUuid(String uuid);

    List<ApprovalEntity> getAllApproval();

    List<ApprovalEntity> getByDepartmentId(Long id);

    void deleteByUuid(String uuid);

    ApprovalEntity getById(Long id);
}
