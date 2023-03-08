package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.ApprovalModuleEntity;
import org.springframework.stereotype.Service;

@Service
public interface ApprovalModuleService {
    ApprovalModuleEntity getById(Long moduleid);

    ApprovalModuleEntity getByUuid(String uuid);

    ApprovalModuleEntity getByName(String name);
}
