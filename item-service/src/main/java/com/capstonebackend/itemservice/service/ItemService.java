package com.capstonebackend.itemservice.service;

import com.capstonebackend.itemservice.entity.ItemEntity;
import com.capstonebackend.itemservice.exception.NotFoundException;
import com.capstonebackend.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

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

}
