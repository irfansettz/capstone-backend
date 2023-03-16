package com.capstone.requestservice.repository;

import com.capstone.requestservice.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReqeustRepository extends JpaRepository<RequestEntity, Long> {
    RequestEntity findByUuid(String uuid);

    List<RequestEntity> findByDepartmentid(Long departmentid);
}
