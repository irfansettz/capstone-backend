package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.UserDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartmentEntity, Long> {
    List<UserDepartmentEntity> findAllByUserid(long id);

    UserDepartmentEntity findByUseridAndDepartmentid(long userid, Long departmentid);
}
