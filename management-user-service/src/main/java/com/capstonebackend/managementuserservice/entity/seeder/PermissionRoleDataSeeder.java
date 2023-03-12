package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.PermissionRoleEntity;
import com.capstonebackend.managementuserservice.repository.PermissionRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(9)
@RequiredArgsConstructor
public class PermissionRoleDataSeeder implements CommandLineRunner {
    private final PermissionRoleRepository permissionRoleRepository;
    @Override
    public void run(String... args) throws Exception {
        if (permissionRoleRepository.count() == 0){
            // superadmin
            PermissionRoleEntity permissionRole1 = new PermissionRoleEntity(null,1L,1L);
            PermissionRoleEntity permissionRole2 = new PermissionRoleEntity(null,1L,2L);
            PermissionRoleEntity permissionRole3 = new PermissionRoleEntity(null,1L,3L);
            PermissionRoleEntity permissionRole4 = new PermissionRoleEntity(null,1L,4L);

            // admin
            PermissionRoleEntity permissionRole5 = new PermissionRoleEntity(null,2L,1L);
            PermissionRoleEntity permissionRole6 = new PermissionRoleEntity(null,2L,2L);
            PermissionRoleEntity permissionRole7 = new PermissionRoleEntity(null,2L,4L);

            // staf
            PermissionRoleEntity permissionRole8 = new PermissionRoleEntity(null,3L,1L);
            PermissionRoleEntity permissionRole9 = new PermissionRoleEntity(null,3L,2L);

            // function manager
            PermissionRoleEntity permissionRole10 = new PermissionRoleEntity(null,4L,1L);
            PermissionRoleEntity permissionRole11 = new PermissionRoleEntity(null,4L,2L);
            PermissionRoleEntity permissionRole12 = new PermissionRoleEntity(null,4L,3L);

            permissionRoleRepository.saveAll(List.of(permissionRole1, permissionRole2, permissionRole3, permissionRole4, permissionRole5, permissionRole6, permissionRole7, permissionRole8, permissionRole9, permissionRole10, permissionRole11, permissionRole12));
        }
    }
}
