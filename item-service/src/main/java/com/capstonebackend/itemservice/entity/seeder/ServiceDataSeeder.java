package com.capstonebackend.itemservice.entity.seeder;

import com.capstonebackend.itemservice.entity.ServiceEntity;
import com.capstonebackend.itemservice.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ServiceDataSeeder implements CommandLineRunner {
    private final ServiceRepository serviceRepository;

    @Override
    public void run(String... args) throws Exception {
        if (serviceRepository.count() == 0){
            ServiceEntity service1 = new ServiceEntity(null, UUID.randomUUID().toString(), "Service 1", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);
            ServiceEntity service2 = new ServiceEntity(null, UUID.randomUUID().toString(), "Service 2", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR", null);

            serviceRepository.saveAll(List.of(service1, service2));
        }
    }
}
