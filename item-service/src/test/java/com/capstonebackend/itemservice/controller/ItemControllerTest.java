package com.capstonebackend.itemservice.controller;

import com.capstonebackend.itemservice.dto.ItemDTO;
import com.capstonebackend.itemservice.dto.ResponseDTO;
import com.capstonebackend.itemservice.entity.ItemEntity;
import com.capstonebackend.itemservice.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private ItemDTO itemDTO;

    @Mock
    private BindingResult bindingResult;

    private ItemController itemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemController = new ItemController(itemService);
    }

    @Test
    void testAddItem() {
        // Arrange
        ItemEntity newItemEntity = new ItemEntity();
        newItemEntity.setId(1L);
        newItemEntity.setName("Test Item");
        newItemEntity.setCreated_by("Test User");
        newItemEntity.setLastupatedby("Test User");

        ItemDTO newItemDTO = new ItemDTO();
        newItemDTO.setName(newItemEntity.getName());
        newItemDTO.setCreated_by(newItemEntity.getCreated_by());
        newItemDTO.setLastupatedby(newItemEntity.getLastupatedby());

        when(itemService.addItem(any(ItemEntity.class))).thenReturn(newItemEntity);

        // Act
        ResponseEntity<ResponseDTO<ItemDTO>> response = itemController.addItem(newItemDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newItemEntity.getName(), response.getBody().getData().getName());
        assertEquals(newItemEntity.getCreated_by(), response.getBody().getData().getCreated_by());
        assertEquals(newItemEntity.getLastupatedby(), response.getBody().getData().getLastupatedby());

        verify(itemService, times(1)).addItem(any(ItemEntity.class));
    }

    @Test
    void testGetAllItems() {
        // Arrange
        List<ItemEntity> itemEntities = new ArrayList<>();
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setId(1L);
        itemEntity1.setName("Item 1");
        itemEntity1.setUuid(UUID.randomUUID().toString());
        itemEntity1.setCreated_by("Test User");
        itemEntities.add(itemEntity1);

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setId(2L);
        itemEntity2.setName("Item 2");
        itemEntity2.setUuid(UUID.randomUUID().toString());
        itemEntity2.setCreated_by("Test User 2");
        itemEntities.add(itemEntity2);

        // Act
        when(itemService.getAllItems()).thenReturn(itemEntities);

        List<ItemEntity> result = itemService.getAllItems();

        // Assert
        Assertions.assertEquals(itemEntities.size(), result.size());
        Assertions.assertEquals(itemEntities.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(itemEntities.get(1).getId(), result.get(1).getId());
        Assertions.assertEquals(itemEntities.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(itemEntities.get(1).getName(), result.get(1).getName());
        Assertions.assertEquals(itemEntities.get(0).getUuid(), result.get(0).getUuid());
        Assertions.assertEquals(itemEntities.get(1).getUuid(), result.get(1).getUuid());
    }

    @Test
    void testGetItemById() {
        // Arrange
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setName("Item 1");
        itemEntity.setUuid(UUID.randomUUID().toString());
        itemEntity.setCreated_by("Test User 1");

        // Act
        when(itemService.getItemById(1L)).thenReturn(Optional.of(itemEntity));

        Optional<ItemEntity> result = itemService.getItemById(1L);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(itemEntity.getId(), result.get().getId());
        Assertions.assertEquals(itemEntity.getName(), result.get().getName());

    }

    @Test
    void testGetItembyUuid() {
        // Arrange
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setName("Item 1");
        itemEntity.setUuid(UUID.randomUUID().toString());
        itemEntity.setCreated_by("Test User 1");

        // Act
        when(itemService.getItemByUuid(itemEntity.getUuid())).thenReturn(Optional.of(itemEntity));

        Optional<ItemEntity> result = itemService.getItemByUuid(itemEntity.getUuid());

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(itemEntity.getId(), result.get().getId());
        Assertions.assertEquals(itemEntity.getName(), result.get().getName());
    }

    @Test
    void testDeleteItemById() {
        // Arrange
        Long id = 1L;
        String expected = "Item has been deleted";

        // Act
        String result = itemController.deleteItembyId(id);

        // Assert
        verify(itemService).deleteItemById(id);
        Assertions.assertEquals(expected, result);
    }

}