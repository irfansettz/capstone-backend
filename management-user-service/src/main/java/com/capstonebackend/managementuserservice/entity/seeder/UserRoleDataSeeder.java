package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.UserRoleEntity;
import com.capstonebackend.managementuserservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Order(5)
public class UserRoleDataSeeder implements CommandLineRunner {
    private final UserRoleRepository userRoleRepository;
    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() == 0){
            UserRoleEntity userRole1 = new UserRoleEntity(1,1L);

            userRoleRepository.save(userRole1);
        }
    }
}
