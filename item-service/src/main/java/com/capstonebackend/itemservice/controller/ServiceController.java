package com.capstonebackend.itemservice.controller;


import com.capstonebackend.itemservice.dto.ResponseDTO;
import com.capstonebackend.itemservice.dto.ServiceDTO;
import com.capstonebackend.itemservice.entity.ServiceEntity;
import com.capstonebackend.itemservice.service.ServiceItemService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
@Data
public class ServiceController {
    private ServiceItemService serviceItemService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ServiceDTO>> addService(@RequestBody ServiceDTO service, Errors errors) {

        ResponseDTO<ServiceDTO> response = new ResponseDTO<>();
        if(errors.hasErrors()) {
            response.setMessage("Failed to add new service");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Create new item using data from the request body
        ServiceEntity addService = new ServiceEntity();
        addService.setUuid(UUID.randomUUID().toString());
        addService.setName(service.getName());
        addService.setCreated_by(service.getCreated_by());
        addService.setLastupatedby(service.getLastupatedby());

        ServiceEntity newItem = serviceItemService.addService(addService);

        ServiceDTO createdService = new ServiceDTO();
        createdService.setUuid(UUID.randomUUID().toString());
        createdService.setName(newItem.getName());
        createdService.setCreated_by(newItem.getCreated_by());
        createdService.setLastupatedby(newItem.getLastupatedby());

        response.setHttpStatus(HttpStatus.OK);
        response.setMessage("Service added successfully");
        response.setData(createdService);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ServiceEntity> getAllServices() {
        return serviceItemService.getAllServices();
    }

    @GetMapping("/{id}")
    public Optional<ServiceEntity> getServiceById(@PathVariable("id") Long id) {
        return serviceItemService.getServiceEntityById(id);
    }

    @GetMapping("/{uuid}")
    public Optional<ServiceEntity> getServiceByUuid(@PathVariable("uuid") String uuid) {
        return serviceItemService.getServiceByUuid(uuid);
    }

    @GetMapping("/{ItemName}")
    public Optional<ServiceEntity> getServiceByName(@PathVariable("ItemName") String ServiceName) {
        return serviceItemService.getServiceByName(ServiceName);
    }
}
