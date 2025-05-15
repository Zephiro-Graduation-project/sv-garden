package com.zephiro.garden.service;

import com.zephiro.garden.entity.*;
import com.zephiro.garden.repository.GardenRepository;
import com.zephiro.garden.repository.InventoryRepository;
import com.zephiro.garden.repository.ShopRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GardenServiceTest {

    private static final String TEST_USER_ID = "67fdcb6afa5d893f602f9b2d";

    @Autowired
    private GardenService gardenService;

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Test
    public void addGarden_shouldCreateNewGardenWithDefaultBackground() {
        // Arrange
        initializeTestInventory();

        // Act
        gardenService.addGarden(TEST_USER_ID);
        Garden garden = gardenRepository.findByUserId(TEST_USER_ID).orElse(null);

        // Assert
        Assertions.assertThat(garden).isNotNull();
        Assertions.assertThat(garden.getUserId()).isEqualTo(TEST_USER_ID);
        Assertions.assertThat(garden.getBackground().getId()).isEqualTo(1); // Background Primavera
        Assertions.assertThat(garden.getFlowers()).hasSize(12);
    }

    @Test
    public void updateGarden_shouldModifyBackgroundUsingRealData() {
        // Arrange
        initializeTestInventory();
        
        // Get background from shop
        Background forestBackground = shopRepository.findAll().get(0).getBackgrounds().stream()
                .filter(b -> b.getId() == 2) // Background Verano
                .findFirst()
                .orElseThrow();

        Garden updateData = new Garden(TEST_USER_ID, forestBackground);

        // Act
        gardenService.updateGarden(TEST_USER_ID, updateData);
        Garden updatedGarden = gardenRepository.findByUserId(TEST_USER_ID).orElse(null);

        // Assert
        Assertions.assertThat(updatedGarden).isNotNull();
        Assertions.assertThat(updatedGarden.getBackground().getId()).isEqualTo(2);
        Assertions.assertThat(updatedGarden.getBackground().getTitle()).isEqualTo("Verano");
    }

    private void initializeTestInventory() {
        if (inventoryRepository.findByUserId(TEST_USER_ID).isEmpty()) {
            Shop shop = shopRepository.findAll().get(0);
            Inventory inventory = new Inventory(
                TEST_USER_ID, 
                shop.getAchievements(), 
                shop.getBackgrounds().get(0) // Background Primavera
            );
            inventoryRepository.save(inventory);
        }
    }
}