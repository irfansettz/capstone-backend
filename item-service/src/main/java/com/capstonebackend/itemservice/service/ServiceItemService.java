package com.capstonebackend.itemservice.service;

import com.capstonebackend.itemservice.entity.ItemEntity;
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
    private final ServiceRepository serviceRepository;

    public List<ServiceEntity> getAllServices() {
        List<ServiceEntity> services = serviceRepository.findAll();
        if (services != null) {
            return services;
        } else {
            throw new NotFoundException(404, "service not found");
        }
    }

    public Optional<ServiceEntity> getServiceById(Long id) {
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if(service.isEmpty()) {
            throw new NotFoundException(404, "service not found");
        }
        return service;
    }

    public ServiceEntity addService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public Optional<ServiceEntity> getServiceByUuid(String uuid) {
        Optional<ServiceEntity> service = serviceRepository.findServiceByUuid(uuid);

        if (!service.isPresent()) {
            throw new NotFoundException(404, "service not found");
        }
        return service;
    }

    public Optional<ServiceEntity> getServiceByName(String name) {
        Optional<ServiceEntity> service = serviceRepository.findServiceByName(name);

        if (!service.isPresent()) {
            throw new NotFoundException(404, "service not found");
        }
        return service;
    }

    public void deleteServiceById(Long id) {
        Optional<ServiceEntity> service = serviceRepository.findById(id);

        if(service.isEmpty()) {
            throw new NotFoundException(404,"service not found");
        }

        serviceRepository.deleteById(id);
    }
}
