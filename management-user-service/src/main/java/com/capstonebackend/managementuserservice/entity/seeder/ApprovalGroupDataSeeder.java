package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.ApprovalGroupEntity;
import com.capstonebackend.managementuserservice.repository.ApprovalGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(7)
public class ApprovalGroupDataSeeder implements CommandLineRunner {
    private final ApprovalGroupRepository approvalGroupRepository;
    @Override
    public void run(String... args) throws Exception {
        if (approvalGroupRepository.count() == 0){
            // superadmin
            ApprovalGroupEntity appGroup1 = new ApprovalGroupEntity(null, 1L, 1L);
            ApprovalGroupEntity appGroup2 = new ApprovalGroupEntity(null, 1L, 2L);
            // function manager
            ApprovalGroupEntity appGroup3 = new ApprovalGroupEntity(null, 4L, 1L);
            ApprovalGroupEntity appGroup4 = new ApprovalGroupEntity(null, 4L, 2L);

            approvalGroupRepository.saveAll(List.of(appGroup1, appGroup2, appGroup3, appGroup4));
        }
    }
}
