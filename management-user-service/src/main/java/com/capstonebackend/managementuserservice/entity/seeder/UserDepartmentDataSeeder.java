package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.UserDepartmentEntity;
import com.capstonebackend.managementuserservice.repository.UserDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Order(4)
public class UserDepartmentDataSeeder implements CommandLineRunner {
    private final UserDepartmentRepository userDepartmentRepository;
    @Override
    public void run(String... args) throws Exception {
        if (userDepartmentRepository.count() == 0){
            UserDepartmentEntity userDept1 = new UserDepartmentEntity(1, 2L);

            userDepartmentRepository.save(userDept1);
        }
    }
}
