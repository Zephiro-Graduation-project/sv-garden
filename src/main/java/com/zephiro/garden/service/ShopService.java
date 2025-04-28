package com.zephiro.garden.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.garden.entity.Achievement;
import com.zephiro.garden.entity.Background;
import com.zephiro.garden.entity.Flower;
import com.zephiro.garden.repository.ShopRepository;

@Service
public class ShopService {
    
    @Autowired
    private ShopRepository shopRepository;

    public List<Flower> getFlowers() {
        // There will be only one shop in the database, so we can use findById with a fixed ID
        return shopRepository.findByName("Zephiro")
                .orElseThrow(() -> new RuntimeException("Zephiro shop was not found"))
                .getFlowers();
    }

    public Flower getFlowerById(int id) {
        // There will be only one shop in the database, so we can use findById with a fixed ID
        return shopRepository.findByName("Zephiro")
                .orElseThrow(() -> new RuntimeException("Zephiro shop was not found"))
                .getFlowers()
                .stream()
                .filter(flower -> flower.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Flower with id " + id + " was not found"));
    }

    public List<Background> getBackgrounds() {
        // There will be only one shop in the database, so we can use findById with a fixed ID
        return shopRepository.findByName("Zephiro")
                .orElseThrow(() -> new RuntimeException("Zephiro shop was not found"))
                .getBackgrounds();
    }

    public Background getBackgroundById(int id) {
        // There will be only one shop in the database, so we can use findById with a fixed ID
        return shopRepository.findByName("Zephiro")
                .orElseThrow(() -> new RuntimeException("Zephiro shop was not found"))
                .getBackgrounds()
                .stream()
                .filter(background -> background.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Background with id " + id + " was not found"));
    }

    public List<Achievement> getAchievements() {
        // There will be only one shop in the database, so we can use findById with a fixed ID
        return shopRepository.findByName("Zephiro")
                .orElseThrow(() -> new RuntimeException("Zephiro shop was not found"))
                .getAchievements();
    }
}
