package com.capstonebackend.itemservice.service;

import com.capstonebackend.itemservice.entity.ServiceEntity;
import com.capstonebackend.itemservice.exception.NotFoundException;
import com.capstonebackend.itemservice.repository.ServiceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class ServiceItemService{
    private ServiceRepository serviceRepository;

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<ServiceEntity> getServiceEntityById(Long id) {
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if(service.isEmpty()) {
            throw new NotFoundException("service not found");
        }
        return service;
    }

    public ServiceEntity addService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public Optional<ServiceEntity> getServiceByUuid(String uuid) {
        Optional<ServiceEntity> service = serviceRepository.findServiceByUuid(uuid);

        if (!service.isPresent()) {
            throw new NotFoundException("service not found");
        }
        return service;
    }

    public Optional<ServiceEntity> getServiceByName(String name) {
        Optional<ServiceEntity> service = serviceRepository.findByServiceName(name);

        if (!service.isPresent()) {
            throw new NotFoundException("service not found");
        }
        return service;
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
