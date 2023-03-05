package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByUuid(String uuid);
}
