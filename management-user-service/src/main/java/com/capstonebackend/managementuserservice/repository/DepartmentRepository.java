package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByUuid(String uuid);

    List<DepartmentEntity> findAllByCode(String code);

    DepartmentEntity findByCode(String code);
}
