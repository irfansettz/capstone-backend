package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.DepartmentEntity;
import com.capstonebackend.managementuserservice.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Order(1)
@RequiredArgsConstructor
public class DepartmentDataSeeder implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        if (departmentRepository.count() == 0){
            DepartmentEntity dept1 = new DepartmentEntity(null, UUID.randomUUID().toString(), "FNC", "FINANCE", true, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
            DepartmentEntity dept2 = new DepartmentEntity(null, UUID.randomUUID().toString(), "IT", "INFORMATION TECHNOLOGY", true, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
            DepartmentEntity dept3 = new DepartmentEntity(null, UUID.randomUUID().toString(), "HRD", "HUMAN RESOURCES & DEVELOPMENT", true, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);

            departmentRepository.saveAll(List.of(dept1, dept2, dept3));
        }
    }
}
