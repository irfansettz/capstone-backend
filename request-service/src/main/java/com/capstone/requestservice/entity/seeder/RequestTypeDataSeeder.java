package com.capstone.requestservice.entity.seeder;

import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.repository.RequestTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestTypeDataSeeder implements CommandLineRunner {
    private final RequestTypeRepository requestTypeRepository;
    @Override
    public void run(String... args) throws Exception {
        if (requestTypeRepository.count() == 0){
            RequestTypeEntity type1 = new RequestTypeEntity(null, UUID.randomUUID().toString(), "Item", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
            RequestTypeEntity type2 = new RequestTypeEntity(null, UUID.randomUUID().toString(), "Service", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);

            requestTypeRepository.saveAll(List.of(type1, type2));
        }
    }
}
