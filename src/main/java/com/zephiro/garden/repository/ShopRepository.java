package com.zephiro.garden.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zephiro.garden.entity.Shop;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {
    
    Optional<Shop> findByName(String name);
}
