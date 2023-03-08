package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.ApprovalGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalGroupRepository extends JpaRepository<ApprovalGroupEntity, Long> {
    List<ApprovalGroupEntity> findAllByRoleid(Long roleid);
}
