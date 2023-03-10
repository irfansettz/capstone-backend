package com.capstonebackend.itemservice.repository;

import com.capstonebackend.itemservice.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    Optional<ServiceEntity> findServiceByName(String name);

    Optional<ServiceEntity> findServiceByUuid(String uuid);
}
