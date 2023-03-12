package com.capstone.requestservice.repository;

import com.capstone.requestservice.entity.RequestDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDetailRepository extends JpaRepository<RequestDetailEntity, Long> {
    RequestDetailEntity findByUuid(String uuid);
}
