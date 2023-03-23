package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.UserEntity;
import com.capstonebackend.managementuserservice.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    protected UserRepository userRepository;

    @Test
    public void addUser(){
        UserEntity user = new UserEntity(1L, "uuuu", "irs", "irs@gmail.com", "sasd", true, null, "sys", null, "sys", null, null);
        when(userRepository.findByUsernameOrEmail("irs", "irs@gmail.com")).thenReturn(null);

        userService.addUser(user);
        verify(userRepository).save(user);
    }

    @Test
    public void getUserByUuid(){
        UserEntity user = new UserEntity(1L, "uuuu", "irs", "irs@gmail.com", "sasd", true, null, "sys", null, "sys", null, null);
        when(userRepository.findByUuid("uuuu")).thenReturn(user);

        UserEntity actual = userService.getUserByUuid("uuuu");
        Assertions.assertEquals(user, actual);
    }

    @Test
    public void getUserByUsername(){
        UserEntity user = new UserEntity(1L, "uuuu", "irs", "irs@gmail.com", "sasd", true, null, "sys", null, "sys", null, null);
        when(userRepository.findByUsername("irs")).thenReturn(user);

        UserEntity actual = userService.getUserByUsername("irs");
        Assertions.assertEquals(user, actual);
    }

    @Test
    public void getAllUIser(){
        UserEntity user = new UserEntity(1L, "uuuu", "irs", "irs@gmail.com", "sasd", true, null, "sys", null, "sys", null, null);
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserEntity> actual = userService.getAllUser();
        Assertions.assertEquals(List.of(user), actual);
    }

    @Test
    public void getUserByEmail(){
        UserEntity user = new UserEntity(1L, "uuuu", "irs", "irs@gmail.com", "sasd", true, null, "sys", null, "sys", null, null);
        when(userRepository.findByEmail("irs@gmail.com")).thenReturn(user);

        UserEntity actual = userService.getUserByEmail("irs@gmail.com");
        Assertions.assertEquals(user, actual);
    }

    @Test
    public void resetPasswordByUuid(){
        UserEntity user = new UserEntity(1L, "uuuu", "irs", "irs@gmail.com", "sasd", true, null, "sys", null, "sys", null, null);
        when(userRepository.findByUuid("uuuu")).thenReturn(user);
        user.setPassword("newpasss");
        userService.resetPasswordByUuid("uuuu", "newpasss");
        verify(userRepository).save(user);
    }
}
