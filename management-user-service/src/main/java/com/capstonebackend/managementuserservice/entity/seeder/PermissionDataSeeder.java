package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.PermissionEntity;
import com.capstonebackend.managementuserservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Order(8)
@RequiredArgsConstructor
public class PermissionDataSeeder implements CommandLineRunner {
    private final PermissionRepository permissionRepository;
    @Override
    public void run(String... args) throws Exception {
        if (permissionRepository.count() == 0){
            PermissionEntity permission1 = new PermissionEntity(null, UUID.randomUUID().toString(), "Dashboard Index", "dashboard.index", "1", true, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
            PermissionEntity permission2 = new PermissionEntity(null, UUID.randomUUID().toString(), "Request Index", "request.index", "2", true, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
            PermissionEntity permission3 = new PermissionEntity(null, UUID.randomUUID().toString(), "Approval Index", "approval.index", "3", true, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
            PermissionEntity permission4 = new PermissionEntity(null, UUID.randomUUID().toString(), "Management User Index", "managementuser.index", "4", true, "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);

            permissionRepository.saveAll(List.of(permission1, permission2, permission3, permission4));
        }
    }
}
