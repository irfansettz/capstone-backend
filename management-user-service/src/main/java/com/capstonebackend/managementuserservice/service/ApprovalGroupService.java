package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.ApprovalGroupEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApprovalGroupService {
    List<ApprovalGroupEntity> getAllByRoleid(Long roleid);
}
