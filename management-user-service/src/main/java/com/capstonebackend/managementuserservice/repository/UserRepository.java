package com.capstonebackend.managementuserservice.repository;

import com.capstonebackend.managementuserservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUuid(String uuid);
}
