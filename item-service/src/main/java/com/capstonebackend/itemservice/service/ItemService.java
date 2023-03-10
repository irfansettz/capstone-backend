package com.capstonebackend.itemservice.service;

import com.capstonebackend.itemservice.dto.ItemDTO;
import com.capstonebackend.itemservice.entity.ItemEntity;
import com.capstonebackend.itemservice.exception.NotFoundException;
import com.capstonebackend.itemservice.repository.ItemRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class ItemService {

    private ItemRepository itemRepository;

    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<ItemEntity> getItemById(Long id) {
        Optional<ItemEntity> item = itemRepository.findById(id);
        if(item.isEmpty()) {
            throw new NotFoundException("Item not found");
        }
        return item;
    }

    public ItemEntity addItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    public Optional<ItemEntity> getItemByUuid(String uuid) {
        Optional<ItemEntity> item = itemRepository.findItemByUuid(uuid);

        if (!item.isPresent()) {
            throw new NotFoundException("Item not found");
        }
        return item;
    }

    public Optional<ItemEntity> getItemByName(String name) {
        Optional<ItemEntity> item = itemRepository.findByItemName(name);

        if (!item.isPresent()) {
            throw new NotFoundException("Item not found");
        }
        return item;
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
