package com.capstonebackend.managementuserservice.service;

import com.capstonebackend.managementuserservice.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {
    UserEntity addUser(UserEntity user);

    UserEntity getUserByUuid(String uuid);

    UserEntity getUserByUsername(String username);
}
