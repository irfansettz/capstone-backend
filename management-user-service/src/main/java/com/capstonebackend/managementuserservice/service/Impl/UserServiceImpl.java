package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.UserEntity;
import com.capstonebackend.managementuserservice.exception.UserIsAny;
import com.capstonebackend.managementuserservice.exception.UserNotFound;
import com.capstonebackend.managementuserservice.repository.UserRepository;
import com.capstonebackend.managementuserservice.service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServices {
    private final UserRepository userRepository;

    @Override
    public UserEntity addUser(UserEntity user) {
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()) != null) throw new UserIsAny("email or username has registered");
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserByUuid(String uuid) {
        if (userRepository.findByUuid(uuid) == null) throw new UserNotFound("user not found");
        return userRepository.findByUuid(uuid);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        if (userRepository.findByUsername(username) == null) throw new UserNotFound("user not found");
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void resetPasswordByUuid(String uuid, String encode) {
        if (userRepository.findByUuid(uuid) == null) throw new UserNotFound("user not found");
        UserEntity user = userRepository.findByUuid(uuid);
        user.setPassword(encode);
        userRepository.save(user);
    }
}
