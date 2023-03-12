package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.RoleEntity;
import com.capstonebackend.managementuserservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Order(2)
@RequiredArgsConstructor
public class RoleDataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0){
            RoleEntity role1 = new RoleEntity(null, UUID.randomUUID().toString(), "SUPERADMIN", "SYSTEM INNJECTOR", null, "SYSTEM INJECTOR", null);
            RoleEntity role2 = new RoleEntity(null, UUID.randomUUID().toString(), "ADMIN", "SYSTEM INNJECTOR", null, "SYSTEM INJECTOR", null);
            RoleEntity role3 = new RoleEntity(null, UUID.randomUUID().toString(), "STAFF", "SYSTEM INNJECTOR", null, "SYSTEM INJECTOR", null);
            RoleEntity role4 = new RoleEntity(null, UUID.randomUUID().toString(), "FUNCTION MANAGER", "SYSTEM INNJECTOR", null, "SYSTEM INJECTOR", null);

            roleRepository.saveAll(List.of(role1, role2, role3, role4));
        }
    }
}
