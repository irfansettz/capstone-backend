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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
@Data
public class ServiceController {
    private final ServiceItemService serviceItemService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ServiceDTO>> addUpdatedService(@RequestBody ServiceDTO service, Errors errors) {

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
        addService.setCreated_on(new Timestamp(System.currentTimeMillis()));

        ServiceEntity newService = serviceItemService.addService(addService);

        ServiceDTO createdService = new ServiceDTO();
        createdService.setId(newService.getId());
        createdService.setUuid(UUID.randomUUID().toString());
        createdService.setName(newService.getName());
        createdService.setCreated_by(newService.getCreated_by());
        createdService.setLastupatedby(newService.getLastupatedby());
        createdService.setCreated_on(newService.getCreated_on());

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
        return serviceItemService.getServiceById(id);
    }

    @GetMapping("uuid/{uuid}")
    public Optional<ServiceEntity> getServiceByUuid(@PathVariable("uuid") String uuid) {
        return serviceItemService.getServiceByUuid(uuid);
    }

//    @GetMapping("name/{ServiceName}")
//    public Optional<ServiceEntity> getServiceByName(@PathVariable("ServiceName") String ServiceName) {
//        return serviceItemService.getServiceByName(ServiceName.replaceAll("\\s+", "%20").toLowerCase());
//    }

    @DeleteMapping("/{id}")
    public String deleteServicebyId(@PathVariable("id") Long id) {

        serviceItemService.deleteServiceById(id);

        return "Item has been deleted";
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ServiceDTO>> updateServiceById(
            @PathVariable("id") Long id,
            @RequestBody ServiceDTO service, Errors errors) {
        ResponseDTO<ServiceDTO> updateService = new ResponseDTO<>();

        if(id == null) {
            updateService.setHttpStatus(HttpStatus.NOT_FOUND);
            updateService.setMessage("Service not found");
            updateService.setData(null);
            return new ResponseEntity<>(updateService, HttpStatus.NOT_FOUND);
        }

        if(errors.hasErrors()) {
            updateService.setMessage("Failed to update new service");
            updateService.setHttpStatus(HttpStatus.BAD_REQUEST);
            updateService.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateService);
        }

        Optional<ServiceEntity> existingService = serviceItemService.getServiceById(id);

        // Create new item using data from the request body
        ServiceEntity addUpdatedService = new ServiceEntity();
        addUpdatedService.setId(existingService.get().getId());
        addUpdatedService.setUuid(UUID.randomUUID().toString());
        addUpdatedService.setName(service.getName());
        addUpdatedService.setCreated_on(existingService.get().getCreated_on());
        addUpdatedService.setCreated_by(service.getCreated_by());
        addUpdatedService.setLastupatedby(service.getLastupatedby());
        addUpdatedService.setLastupdatedon(service.getLastupdatedon());

        ServiceEntity newService = serviceItemService.addService(addUpdatedService);

        ServiceDTO createdService = new ServiceDTO();
        createdService.setId(existingService.get().getId());
        createdService.setUuid(UUID.randomUUID().toString());
        createdService.setName(newService.getName());
        createdService.setCreated_on(newService.getCreated_on());
        createdService.setCreated_by(newService.getCreated_by());
        createdService.setLastupatedby(newService.getLastupatedby());
        createdService.setLastupdatedon(new Timestamp(System.currentTimeMillis()));

        updateService.setHttpStatus(HttpStatus.OK);
        updateService.setMessage("Service updated successfully");
        updateService.setData(createdService);

        return new ResponseEntity<>(updateService, HttpStatus.OK);
    }
}
