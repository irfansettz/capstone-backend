package com.capstonebackend.itemservice.repository;

import com.capstonebackend.itemservice.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findItemByName(String name);

    Optional<ItemEntity> findItemByUuid(String uuid);

}
