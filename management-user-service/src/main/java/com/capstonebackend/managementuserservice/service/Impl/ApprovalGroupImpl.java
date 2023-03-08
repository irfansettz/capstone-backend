package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.ApprovalGroupEntity;
import com.capstonebackend.managementuserservice.repository.ApprovalGroupRepository;
import com.capstonebackend.managementuserservice.service.ApprovalGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalGroupImpl implements ApprovalGroupService {
    private final ApprovalGroupRepository approvalGroupRepository;

    @Override
    public List<ApprovalGroupEntity> getAllByRoleid(Long roleid) {
        return approvalGroupRepository.findAllByRoleid(roleid);
    }
}
