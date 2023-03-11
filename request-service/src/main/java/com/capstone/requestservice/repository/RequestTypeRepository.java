package com.capstone.requestservice.repository;

import com.capstone.requestservice.entity.RequestTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestTypeRepository extends JpaRepository<RequestTypeEntity, Long> {
    RequestTypeEntity findByUuid(String uuid);
}
