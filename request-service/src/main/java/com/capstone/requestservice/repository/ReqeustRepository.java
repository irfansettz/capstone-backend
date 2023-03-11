package com.capstone.requestservice.repository;

import com.capstone.requestservice.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqeustRepository extends JpaRepository<RequestEntity, Long> {
    RequestEntity findByUuid(String uuid);
}
