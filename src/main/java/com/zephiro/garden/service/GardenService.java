package com.zephiro.garden.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.garden.entity.Achievement;
import com.zephiro.garden.entity.Background;
import com.zephiro.garden.entity.Flower;
import com.zephiro.garden.entity.Garden;
import com.zephiro.garden.repository.GardenRepository;
import com.zephiro.garden.repository.InventoryRepository;

@Service
public class GardenService {
    
    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    public void addGarden(String userId) {
        Garden existingGarden = gardenRepository.findByUserId(userId).orElse(null);
        if (existingGarden != null) {
            throw new RuntimeException("Garden already exists for user with id: " + userId);
        } else {
            Background background = inventoryService.getBackgroundById(userId, 1);
            Garden garden = new Garden(userId, background);
            gardenRepository.save(garden);
        }
    }

    public Garden getGarden(String userId) {
        Achievement ua = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"))
                .getAchievements()
                .stream()
                .filter(achievement -> achievement.getId() == 8)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));

        Garden existingGarden = gardenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));

        if(ua.isCompleted()) {
            existingGarden.setState(true);
            gardenRepository.save(existingGarden);
        }

        return existingGarden;
    }

    public Flower getFlowerById(String userId, int id) {
        List<Flower> flowers = gardenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"))
                .getFlowers();

        for (Flower flower : flowers) {
            if (flower != null && flower.getId() == id) {
                return flower;
            }
        }
        throw new RuntimeException("Flower with id " + id + " was not found");
    }

    public void updateGarden(String userId, Garden garden) {
        Garden existingGarden = gardenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));
        
        existingGarden.setBackground(garden.getBackground());
        existingGarden.setFlowers(garden.getFlowers());
        
        gardenRepository.save(existingGarden);
    }

    //* Development team only 
    public void dailyReset() {
        // Get the inventory of all users
        List<Garden> gardens = gardenRepository.findAll();

        // Iterate through each inventory and reset the daily achievements
        for (Garden garden : gardens) {
            garden.setState(false);
            gardenRepository.save(garden);
        }
    }
}
