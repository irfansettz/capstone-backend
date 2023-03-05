package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    List<UserRoleEntity> findAllByUserid(Long id);

    UserRoleEntity findByUseridAndRoleid(long userid, Long roleid);
}
