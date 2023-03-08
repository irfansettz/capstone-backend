package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.ApprovalModuleEntity;
import com.capstonebackend.managementuserservice.repository.ApprovalModuleRepository;
import com.capstonebackend.managementuserservice.service.ApprovalModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovalModuleImpl implements ApprovalModuleService {
    private final ApprovalModuleRepository approvalModuleRepository;

    @Override
    public ApprovalModuleEntity getById(Long moduleid) {
        return approvalModuleRepository.findById(moduleid).get();
    }

    @Override
    public ApprovalModuleEntity getByUuid(String uuid) {
        return approvalModuleRepository.findByUuid(uuid);
    }

    @Override
    public ApprovalModuleEntity getByName(String name) {
        return approvalModuleRepository.findByName(name);
    }
}
