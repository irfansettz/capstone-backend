package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.UserEntity;
import com.capstonebackend.managementuserservice.repository.UserRepository;
import com.capstonebackend.managementuserservice.service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServices {
    private final UserRepository userRepository;

    @Override
    public UserEntity addUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }
}
