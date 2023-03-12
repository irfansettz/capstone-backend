package com.capstonebackend.itemservice.entity.seeder;

import com.capstonebackend.itemservice.entity.ItemEntity;
import com.capstonebackend.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemDataSeeder implements CommandLineRunner {
    private final ItemRepository itemRepository;
    @Override
    public void run(String... args) throws Exception {
        if (itemRepository.count() == 0) {
            ItemEntity item1 = new ItemEntity(null, UUID.randomUUID().toString(), "Computer", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR",null);
            ItemEntity item2 = new ItemEntity(null, UUID.randomUUID().toString(), "Laptop", "SYSTEM INJECTOR", null, "SYSTEM INJECTOR",null);

            itemRepository.saveAll(List.of(item1, item2));
        }
    }
}
