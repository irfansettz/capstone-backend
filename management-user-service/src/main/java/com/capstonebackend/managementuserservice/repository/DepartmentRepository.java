package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByUuid(String uuid);
}
