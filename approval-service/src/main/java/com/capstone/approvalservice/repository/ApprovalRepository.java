package com.capstone.approvalservice.repository;

import com.capstone.approvalservice.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    ApprovalEntity findByUuid(String uuid);

    List<ApprovalEntity> findAllByDepartmentid(Long id);
}
