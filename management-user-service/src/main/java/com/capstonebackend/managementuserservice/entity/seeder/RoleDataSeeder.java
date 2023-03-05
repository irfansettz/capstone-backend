package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import com.capstonebackend.managementuserservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Order(2)
@RequiredArgsConstructor
public class RoleDataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        RoleEntity role1 = new RoleEntity(null, UUID.randomUUID().toString(), "ADMMIN", "SYSTEM INNJECTOR", null, "SYSTEM INJECTOR", null);
        RoleEntity role2 = new RoleEntity(null, UUID.randomUUID().toString(), "HEAD FINANCE", "SYSTEM INNJECTOR", null, "SYSTEM INJECTOR", null);
        RoleEntity role3 = new RoleEntity(null, UUID.randomUUID().toString(), "HEAD IT", "SYSTEM INNJECTOR", null, "SYSTEM INJECTOR", null);

        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
    }
}
