package com.capstonebackend.managementuserservice.entity.seeder;

import com.capstonebackend.managementuserservice.entity.ApprovalModuleEntity;
import com.capstonebackend.managementuserservice.repository.ApprovalModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Order(6)
public class ApprovalModuleDataSeeder implements CommandLineRunner {
    private final ApprovalModuleRepository approvalModuleRepository;
    @Override
    public void run(String... args) throws Exception {
        ApprovalModuleEntity module1 = new ApprovalModuleEntity(null, UUID.randomUUID().toString(), "Approval Head Dept", "approval request procurement for head department", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
        ApprovalModuleEntity module2 = new ApprovalModuleEntity(null, UUID.randomUUID().toString(), "Approval Head FNC", "approval request procurement for head FNC", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);

        approvalModuleRepository.saveAll(List.of(module1, module2));
    }
}
