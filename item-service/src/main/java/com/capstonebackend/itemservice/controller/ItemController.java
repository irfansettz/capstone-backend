package com.capstonebackend.itemservice.controller;

import com.capstonebackend.itemservice.dto.ItemDTO;
import com.capstonebackend.itemservice.dto.ResponseDTO;
import com.capstonebackend.itemservice.entity.ItemEntity;
import com.capstonebackend.itemservice.service.ItemService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@Data
public class ItemController {

    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ItemDTO>> addItem(@RequestBody ItemDTO item, Errors errors) {

        ResponseDTO<ItemDTO> response = new ResponseDTO<>();
        if(errors.hasErrors()) {
            response.setMessage("Failed to add new item");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Create new item using data from the request body
        ItemEntity addItem = new ItemEntity();
        addItem.setUuid(UUID.randomUUID().toString());
        addItem.setName(item.getName());
        addItem.setCreated_by(item.getCreated_by());
        addItem.setLastupatedby(item.getLastupatedby());

        ItemEntity newItem = itemService.addItem(addItem);

        ItemDTO createdItem = new ItemDTO();
        createdItem.setUuid(UUID.randomUUID().toString());
        createdItem.setName(newItem.getName());
        createdItem.setCreated_by(newItem.getCreated_by());
        createdItem.setLastupatedby(newItem.getLastupatedby());

        response.setHttpStatus(HttpStatus.OK);
        response.setMessage("Item added successfully");
        response.setData(createdItem);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ItemEntity> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<ItemEntity> getItemById(@PathVariable("id") Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/{uuid}")
    public Optional<ItemEntity> getItembyUuid(@PathVariable("uuid") String uuid) {
        return itemService.getItemByUuid(uuid);
    }

    @GetMapping("/{ItemName}")
    public Optional<ItemEntity> getItemByName(@PathVariable("ItemName") String ItemName) {
        return itemService.getItemByName(ItemName);
    }
}
