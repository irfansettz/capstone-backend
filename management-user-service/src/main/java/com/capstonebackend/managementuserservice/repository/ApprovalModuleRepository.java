package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.ApprovalModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalModuleRepository extends JpaRepository<ApprovalModuleEntity, Long> {
    ApprovalModuleEntity findByUuid(String uuid);

    ApprovalModuleEntity findByName(String name);
}
