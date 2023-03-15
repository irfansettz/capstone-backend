package com.capstonebackend.itemservice.service;

import com.capstonebackend.itemservice.entity.ItemEntity;
import com.capstonebackend.itemservice.exception.NotFoundException;
import com.capstonebackend.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    @Cacheable("items")
    public List<ItemEntity> getAllItems() {
        List<ItemEntity> items = itemRepository.findAll();
        if (items != null) {
            return items;
        } else {
            throw new NotFoundException(404, "Item not found");
        }

    }

    public Optional<ItemEntity> getItemById(Long id) {
        Optional<ItemEntity> item = itemRepository.findById(id);
        if(item.isEmpty()) {
            throw new NotFoundException(404, "Item not found");
        }
        return item;
    }

    public ItemEntity addItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    public Optional<ItemEntity> getItemByUuid(String uuid) {
        Optional<ItemEntity> item = itemRepository.findItemByUuid(uuid);

        if (!item.isPresent()) {
            throw new NotFoundException(404, "Item not found");
        }
        return item;
    }

    public Optional<ItemEntity> getItemByName(String name) {
        Optional<ItemEntity> item = itemRepository.findItemByName(name);

        if (!item.isPresent()) {
            throw new NotFoundException(404, "Item not found");
        }
        return item;
    }

    public void deleteItemById(Long id) {
        Optional<ItemEntity> item = itemRepository.findById(id);

        if(item.isEmpty()) {
            throw new NotFoundException(404,"Item not found");
        }

        itemRepository.deleteById(id);
    }

    // Imitate Bad Network or slow internet conn
    private void sleep(int seconds) {
        try {
            SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
