package com.zephiro.garden.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zephiro.garden.entity.Inventory;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    
    Optional<Inventory> findByUserId(String id);
}
