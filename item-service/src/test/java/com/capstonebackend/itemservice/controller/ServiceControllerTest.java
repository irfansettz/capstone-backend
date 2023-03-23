package com.capstonebackend.itemservice.controller;


import com.capstonebackend.itemservice.dto.ServiceDTO;
import com.capstonebackend.itemservice.dto.ResponseDTO;
import com.capstonebackend.itemservice.dto.ServiceDTO;
import com.capstonebackend.itemservice.entity.ServiceEntity;
import com.capstonebackend.itemservice.service.ServiceItemService;
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

class ServiceControllerTest {

    @Mock
    private ServiceItemService serviceItemService;

    @Mock
    private ServiceDTO serviceDTO;

    @Mock
    private BindingResult bindingResult;

    private ServiceController serviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceController = new ServiceController(serviceItemService);
    }

    @Test
    void testAddService() {
        // Arrange
        ServiceEntity newServiceEntity = new ServiceEntity();
        newServiceEntity.setId(1L);
        newServiceEntity.setName("Test Service");
        newServiceEntity.setCreated_by("Test User");
        newServiceEntity.setLastupatedby("Test User");

        ServiceDTO newServiceDTO = new ServiceDTO();
        newServiceDTO.setName(newServiceEntity.getName());
        newServiceDTO.setCreated_by(newServiceEntity.getCreated_by());
        newServiceDTO.setLastupatedby(newServiceEntity.getLastupatedby());

        when(serviceItemService.addService(any(ServiceEntity.class))).thenReturn(newServiceEntity);

        // Act
        ResponseEntity<ResponseDTO<ServiceDTO>> response = serviceController.addUpdatedService(newServiceDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newServiceEntity.getName(), response.getBody().getData().getName());
        assertEquals(newServiceEntity.getCreated_by(), response.getBody().getData().getCreated_by());
        assertEquals(newServiceEntity.getLastupatedby(), response.getBody().getData().getLastupatedby());

        verify(serviceItemService, times(1)).addService(any(ServiceEntity.class));
    }

    @Test
    void testGetAllServices() {
        // Arrange
        List<ServiceEntity> serviceEntities = new ArrayList<>();
        ServiceEntity serviceEntity1 = new ServiceEntity();
        serviceEntity1.setId(1L);
        serviceEntity1.setName("Item 1");
        serviceEntity1.setUuid(UUID.randomUUID().toString());
        serviceEntity1.setCreated_by("Test User");
        serviceEntities.add(serviceEntity1);

        ServiceEntity serviceEntity2 = new ServiceEntity();
        serviceEntity2.setId(2L);
        serviceEntity2.setName("Item 2");
        serviceEntity2.setUuid(UUID.randomUUID().toString());
        serviceEntity2.setCreated_by("Test User 2");
        serviceEntities.add(serviceEntity2);

        // Act
        when(serviceItemService.getAllServices()).thenReturn(serviceEntities);

        List<ServiceEntity> result = serviceItemService.getAllServices();

        // Assert
        Assertions.assertEquals(serviceEntities.size(), result.size());
        Assertions.assertEquals(serviceEntities.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(serviceEntities.get(1).getId(), result.get(1).getId());
        Assertions.assertEquals(serviceEntities.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(serviceEntities.get(1).getName(), result.get(1).getName());
        Assertions.assertEquals(serviceEntities.get(0).getUuid(), result.get(0).getUuid());
        Assertions.assertEquals(serviceEntities.get(1).getUuid(), result.get(1).getUuid());
    }

    @Test
    void testGetServiceById() {
        // Arrange
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(1L);
        serviceEntity.setName("Service 1");
        serviceEntity.setUuid(UUID.randomUUID().toString());
        serviceEntity.setCreated_by("Test User 1");

        // Act
        when(serviceItemService.getServiceById(1L)).thenReturn(Optional.of(serviceEntity));

        Optional<ServiceEntity> result = serviceItemService.getServiceById(1L);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(serviceEntity.getId(), result.get().getId());
        Assertions.assertEquals(serviceEntity.getName(), result.get().getName());

    }

    @Test
    void testGetServicebyUuid() {
        // Arrange
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(1L);
        serviceEntity.setName("Service 1");
        serviceEntity.setUuid(UUID.randomUUID().toString());
        serviceEntity.setCreated_by("Test User 1");

        // Act
        when(serviceItemService.getServiceByUuid(serviceEntity.getUuid())).thenReturn(Optional.of(serviceEntity));

        Optional<ServiceEntity> result = serviceItemService.getServiceByUuid(serviceEntity.getUuid());

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(serviceEntity.getId(), result.get().getId());
        Assertions.assertEquals(serviceEntity.getName(), result.get().getName());
    }

    @Test
    void testDeleteServiceById() {
        // Arrange
        Long id = 1L;
        String expected = "Service has been deleted";

        // Act
        String result = serviceController.deleteServicebyId(id);

        // Assert
        verify(serviceItemService).deleteServiceById(id);
        Assertions.assertEquals(expected, result);
    }

}