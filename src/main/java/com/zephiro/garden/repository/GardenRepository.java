package com.zephiro.garden.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zephiro.garden.entity.Garden;

@Repository
public interface GardenRepository extends MongoRepository<Garden, String> {
    
    Optional<Garden> findByUserId(String id);
}
