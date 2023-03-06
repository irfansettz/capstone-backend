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
        UserDepartmentEntity userDept1 = new UserDepartmentEntity(1, 1L);
        UserDepartmentEntity userDept2 = new UserDepartmentEntity(1, 2L);
        UserDepartmentEntity userDept3 = new UserDepartmentEntity(1, 3L);

        userDepartmentRepository.saveAll(List.of(userDept1, userDept2, userDept3));
    }
}
