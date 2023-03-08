package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.PermissionRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRoleRepository extends JpaRepository<PermissionRoleEntity, Long> {
    List<PermissionRoleEntity> findAllByRoleid(Long roleid);
}
