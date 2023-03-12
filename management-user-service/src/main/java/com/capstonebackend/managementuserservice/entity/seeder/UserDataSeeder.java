package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.UserEntity;
import com.capstonebackend.managementuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Order(3)
public class UserDataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0){
            UserEntity user = new UserEntity(null, UUID.randomUUID().toString(), "SUPERADMIN", "superadmin@gmail.com", passwordEncoder.encode("12345678"), true, null, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null, null);
            userRepository.save(user);
        }
    }
}
