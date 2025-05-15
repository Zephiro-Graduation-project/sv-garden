package com.zephiro.garden.service;

import com.zephiro.garden.entity.*;
import com.zephiro.garden.repository.InventoryRepository;
import com.zephiro.garden.repository.ShopRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InventoryServiceTest {

    private static final String TEST_USER_ID = "67fdcb6afa5d893f602f9b2d";

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @BeforeEach
    public void setup() {
        initializeTestInventory();
    }

    @Test
    public void buyFlower_shouldAddDaisyToInventory() {
        // Arrange
        Flower daisy = shopRepository.findAll().get(0).getFlowers().stream()
                .filter(f -> f.getId() == 1) // Daisy
                .findFirst()
                .orElseThrow();
        
        // Add coins to user
        Inventory inventory = inventoryRepository.findByUserId(TEST_USER_ID).orElseThrow();
        inventory.setCoins(100);
        inventoryRepository.save(inventory);

        // Act
        inventoryService.buyFlower(TEST_USER_ID, daisy);
        Inventory updatedInventory = inventoryRepository.findByUserId(TEST_USER_ID).orElse(null);

        // Assert
        Assertions.assertThat(updatedInventory).isNotNull();
        Assertions.assertThat(updatedInventory.getFlowers())
            .extracting(Flower::getId)
            .contains(1);
        Assertions.assertThat(updatedInventory.getCoins()).isEqualTo(70); // 100 - 30 (price of Daisy)
    }

    @Test
    public void buyBackground_shouldAddSummerBackground() {
        // Arrange
        Background summerBackground = shopRepository.findAll().get(0).getBackgrounds().stream()
                .filter(b -> b.getId() == 2) // Verano
                .findFirst()
                .orElseThrow();
        
        // Add coins to user
        Inventory inventory = inventoryRepository.findByUserId(TEST_USER_ID).orElseThrow();
        inventory.setCoins(250);
        inventoryRepository.save(inventory);

        // Act
        inventoryService.buyBackground(TEST_USER_ID, summerBackground);
        Inventory updatedInventory = inventoryRepository.findByUserId(TEST_USER_ID).orElse(null);

        // Assert
        Assertions.assertThat(updatedInventory).isNotNull();
        Assertions.assertThat(updatedInventory.getBackgrounds())
            .extracting(Background::getId)
            .contains(2);
        Assertions.assertThat(updatedInventory.getCoins()).isEqualTo(50); // 250 - 200 (price of Verano)
    }

    @Test
    public void rewardStreak_shouldComplete5DayAchievement() {
        // Arrange
        Inventory inventory = inventoryRepository.findByUserId(TEST_USER_ID).orElseThrow();
        int initialCoins = inventory.getCoins();

        // Act
        inventoryService.rewardStreak(TEST_USER_ID, 5);
        Inventory updatedInventory = inventoryRepository.findByUserId(TEST_USER_ID).orElse(null);

        // Assert
        Assertions.assertThat(updatedInventory).isNotNull();
        Assertions.assertThat(updatedInventory.getAchievements().get(0).isCompleted()).isTrue();
        Assertions.assertThat(updatedInventory.getCoins()).isEqualTo(initialCoins + 10); 
    }

    private void initializeTestInventory() {
        if (inventoryRepository.findByUserId(TEST_USER_ID).isEmpty()) {
            Shop shop = shopRepository.findAll().get(0);
            Inventory inventory = new Inventory(
                TEST_USER_ID, 
                shop.getAchievements(), 
                shop.getBackgrounds().get(0) // Primavera background
            );
            inventory.setCoins(0);
            inventoryRepository.save(inventory);
        }
    }
}