package com.capstonebackend.itemservice.controller;

import com.capstonebackend.itemservice.dto.ItemDTO;
import com.capstonebackend.itemservice.dto.ResponseDTO;
import com.capstonebackend.itemservice.entity.ItemEntity;
import com.capstonebackend.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ItemDTO>> addItem(@Valid @RequestBody ItemDTO item, BindingResult errors) {

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
        addItem.setCreated_on(new Timestamp(System.currentTimeMillis()));

        ItemEntity newItem = itemService.addItem(addItem);

        ItemDTO createdItem = new ItemDTO();
        createdItem.setId(newItem.getId());
        createdItem.setUuid(UUID.randomUUID().toString());
        createdItem.setName(newItem.getName());
        createdItem.setCreated_by(newItem.getCreated_by());
        createdItem.setLastupatedby(newItem.getLastupatedby());
        createdItem.setCreated_on(newItem.getCreated_on());

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

    @GetMapping("uuid/{uuid}")
    public Optional<ItemEntity> getItembyUuid(@PathVariable("uuid") String uuid) {
        return itemService.getItemByUuid(uuid);
    }




    @DeleteMapping("/{id}")
    public String deleteItembyId(@PathVariable("id") Long id) {
        itemService.deleteItemById(id);

        return "Item has been deleted";
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ItemDTO>> updateItemById(
            @PathVariable("id") Long id,
            @RequestBody ItemDTO item, Errors errors) {
        ResponseDTO<ItemDTO> updateItem = new ResponseDTO<>();

        if(id == null) {
            updateItem.setHttpStatus(HttpStatus.NOT_FOUND);
            updateItem.setMessage("Item not found");
            updateItem.setData(null);
            return new ResponseEntity<>(updateItem, HttpStatus.NOT_FOUND);
        }

        if(errors.hasErrors()) {
            updateItem.setMessage("Failed to update new item");
            updateItem.setHttpStatus(HttpStatus.BAD_REQUEST);
            updateItem.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateItem);
        }

        Optional<ItemEntity> existingItem = itemService.getItemById(id);

        // Create new item using data from the request body
        ItemEntity updatedItem = new ItemEntity();
        updatedItem.setId(existingItem.get().getId());
        updatedItem.setUuid(UUID.randomUUID().toString());
        updatedItem.setName(item.getName());
        updatedItem.setCreated_by(item.getCreated_by());
        updatedItem.setCreated_on(existingItem.get().getCreated_on());
        updatedItem.setLastupatedby(item.getLastupatedby());
        updatedItem.setLastupdatedon(item.getLastupdatedon());

        ItemEntity newItem = itemService.addItem(updatedItem);

        ItemDTO createdItem = new ItemDTO();
        createdItem.setId(existingItem.get().getId());
        createdItem.setId(newItem.getId());
        createdItem.setUuid(UUID.randomUUID().toString());
        createdItem.setName(newItem.getName());
        createdItem.setCreated_by(newItem.getCreated_by());
        createdItem.setCreated_on(existingItem.get().getCreated_on());
        createdItem.setLastupatedby(newItem.getLastupatedby());
        createdItem.setLastupdatedon(new Timestamp(System.currentTimeMillis()));

        updateItem.setHttpStatus(HttpStatus.OK);
        updateItem.setMessage("Item updated successfully");
        updateItem.setData(createdItem);

        return new ResponseEntity<>(updateItem, HttpStatus.OK);
    }
}
