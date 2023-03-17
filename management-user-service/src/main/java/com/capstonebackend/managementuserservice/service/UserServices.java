package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    UserEntity addUser(UserEntity user);

    UserEntity getUserByUuid(String uuid);

    UserEntity getUserByUsername(String username);

    List<UserEntity> getAllUser();

    UserEntity getUserByEmail(String email);

    void resetPasswordByUuid(String uuid, String encode);
}
