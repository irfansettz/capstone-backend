package com.capstone.approvalservice.service.Impl;

import com.capstone.approvalservice.dto.StatusDTO;
import com.capstone.approvalservice.entity.ApprovalEntity;
import com.capstone.approvalservice.repository.ApprovalRepository;
import com.capstone.approvalservice.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final ApprovalRepository approvalRepository;

    @Override
    public Long addApproval(ApprovalEntity approvalSave) {
        return approvalRepository.save(approvalSave).getId();
    }

    @Override
    public void updateStatusByUuid(String uuid, String username, StatusDTO status) {
        ApprovalEntity approval = approvalRepository.findByUuid(uuid);
        approval.setStatus(status.getStatus());
        approval.setLastupdatedby(username);
        approval.setLastupdatedon(null);
        approvalRepository.save(approval);
    }

    @Override
    public ApprovalEntity getByUuid(String uuid) {
        return approvalRepository.findByUuid(uuid);
    }

    @Override
    public List<ApprovalEntity> getAllApproval() {
        return approvalRepository.findAll();
    }

    @Override
    public List<ApprovalEntity> getByDepartmentId(Long id) {
        return approvalRepository.findAllByDepartmentid(id);
    }

    @Override
    public void deleteByUuid(String uuid) {
        ApprovalEntity approval = approvalRepository.findByUuid(uuid);
        approvalRepository.delete(approval);
    }

    @Override
    public ApprovalEntity getById(Long id) {
        return approvalRepository.findById(id).get();
    }
}
