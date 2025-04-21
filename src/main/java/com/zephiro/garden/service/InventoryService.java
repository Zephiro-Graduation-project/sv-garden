package com.zephiro.garden.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.garden.entity.Achievement;
import com.zephiro.garden.entity.Background;
import com.zephiro.garden.entity.Flower;
import com.zephiro.garden.entity.Inventory;
import com.zephiro.garden.error.ItemAlreadyOwnedException;
import com.zephiro.garden.error.NotEnoughCoinsException;
import com.zephiro.garden.repository.InventoryRepository;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ShopService shopService;
    
    public void addInventory(String userId) {
        // Create a new inventory for the user with the given userId if it doesn't exist
        Inventory existingInventory = inventoryRepository.findByUserId(userId).orElse(null);
        if (existingInventory != null) {
            throw new RuntimeException("Inventory already exists for user with id: " + userId);
        } else {
            Inventory inventory = new Inventory(userId, shopService.getAchievements(), shopService.getBackgroundById(1));
            inventoryRepository.save(inventory);
        }
    }

    public List<Flower> getFlowersOnInventory(String userId) {
        return inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"))
                .getFlowers();
    }

    public Flower getFlowerById(String userId, int id) {
        return inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"))
                .getFlowers()
                .stream()
                .filter(flower -> flower.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Flower with id " + id + " was not found"));
    }

    public List<Background> getBackgrounds(String userId) {
        return inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"))
                .getBackgrounds();
    }

    public Background getBackgroundById(String userId, int id) {
        return inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"))
                .getBackgrounds()
                .stream()
                .filter(background -> background.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Background with id " + id + " was not found"));
    }

    public int getUserCoins(String userId) {
        return inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"))
                .getCoins();
    }

    public void buyFlower(String userId, Flower flower) {
        // Get the inventory of the user with the given userId
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));

        // Get the flowers from the inventory
        List<Flower> flowers = inventory.getFlowers();

        // Check if the user has enough coins to buy the flower
        if (inventory.getCoins() < flower.getPrice()) {
            throw new NotEnoughCoinsException(userId);
        } else {
            // Check if the flower is already in the inventory
            if (flowers.stream().anyMatch(f -> f.getId() == flower.getId())) {
                throw new ItemAlreadyOwnedException(flower.getId());
            } else {
                // Add the flower to the inventory and take the coins
                flowers.add(flower);
                inventory.setFlowers(flowers);
                inventory.setCoins(inventory.getCoins() - flower.getPrice());
            }
        }

        // Save the updated inventory
        inventoryRepository.save(inventory);
    }

    public void buyBackground(String userId, Background background) {
        // Get the inventory of the user with the given userId
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));

        // Get the backgrounds from the inventory
        List<Background> backgrounds = inventory.getBackgrounds();

        // Check if the user has enough coins to buy the background
        if (inventory.getCoins() < background.getPrice()) {
            throw new NotEnoughCoinsException(userId);
        } else {
            // Check if the background is already in the inventory
            if (backgrounds.stream().anyMatch(b -> b.getId() == background.getId())) {
                throw new ItemAlreadyOwnedException(background.getId());
            } else {
                // Add the background to the inventory and take the coins
                backgrounds.add(background);
                inventory.setBackgrounds(backgrounds);
                inventory.setCoins(inventory.getCoins() - background.getPrice());
            }
        }

        // Save the updated inventory
        inventoryRepository.save(inventory);
    }

    public void rewardEmergencyContact(String userId) {
        // Get the inventory of the user with the given userId
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));
        
        // Get the achivement #6 (Emergency Contact) from the inventory
        List<Achievement> allUa = inventory.getAchievements();
        Achievement ua = allUa.get(5);

        if(ua.isCompleted())
            return;
        else {
            ua.setCompleted(true);
            allUa.set(5, ua);
            inventory.setAchievements(allUa);

            // Add coins to the user's inventory
            inventory.setCoins(inventory.getCoins() + ua.getReward());

            // Save the updated inventory
            inventoryRepository.save(inventory);
        }
    }

    public void rewardStreak(String userId, int streak) {
        // Get the inventory of the user with the given userId
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));
        inventory.setStreak(streak);
        
        List<Achievement> allUa = inventory.getAchievements();
        Achievement ua;
        int index;

        if(streak == 5) {
            index = 0;
            ua = allUa.get(index);
        } else if(streak == 10) {
            index = 1;
            ua = allUa.get(index);
        } else if(streak == 20) {
            index = 2;
            ua = allUa.get(index);
        } else if(streak == 30) {
            index = 3;
            ua = allUa.get(index);
        } else if(streak == 50) {
            index = 4;
            ua = allUa.get(index);
        } else {
            inventoryRepository.save(inventory);
            return;
        }

        if(ua.isCompleted())
            return;
        else {
            ua.setCompleted(true);
            allUa.set(index, ua);
            inventory.setAchievements(allUa);

            // Add coins to the user's inventory
            inventory.setCoins(inventory.getCoins() + ua.getReward());

            // Save the updated inventory
            inventoryRepository.save(inventory);
        }
    }

    public void rewardSurvey(String userId) {
        // Get the inventory of the user with the given userId
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));
        
        List<Achievement> allUa = inventory.getAchievements();
        Achievement ua = allUa.get(7);
        ua.setCompleted(true);
        allUa.set(7, ua);
        inventory.setAchievements(allUa);

        // Add coins to the user's inventory
        inventory.setCoins(inventory.getCoins() + ua.getReward());

        // Save the updated inventory
        inventoryRepository.save(inventory);
    }

    public void rewardContent(String userId){
        // Get the inventory of the user with the given userId
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with the id: " + userId + " was not found"));
        
        List<Achievement> allUa = inventory.getAchievements();
        Achievement ua = allUa.get(6);

        if(ua.isCompleted())
            return;
        else {
            ua.setCompleted(true);
            allUa.set(6, ua);
            inventory.setAchievements(allUa);

            // Add coins to the user's inventory
            inventory.setCoins(inventory.getCoins() + ua.getReward());

            // Save the updated inventory
            inventoryRepository.save(inventory);
        }
    }

    //* Development team only 
    public void dailyReset() {
        // Get the inventory of all users
        List<Inventory> inventories = inventoryRepository.findAll();

        // Iterate through each inventory and reset the daily achievements
        for (Inventory inventory : inventories) {
            List<Achievement> allUa = inventory.getAchievements();
            for (int i=0; i < inventory.getAchievements().size(); i++) {
                Achievement achievement = inventory.getAchievements().get(i);
                if (achievement.getType().equals("daily")) {
                    if (achievement.isCompleted()) {
                        achievement.setCompleted(false);
                        allUa.set(i, achievement);
                    }
                }
            }
            inventory.setAchievements(allUa);
            inventoryRepository.save(inventory);
        }
    }
}
