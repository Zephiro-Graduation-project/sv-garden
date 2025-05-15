package com.zephiro.garden.repository;

import com.zephiro.garden.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.Optional;

@DataMongoTest
public class InventoryRepositoryTest {

    private static final String TEST_USER_ID = "67fdcb6afa5d893f602f9b2d";

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void saveInventory_shouldPersistWithCorrectFields() {
        // Arrange
        Achievement achievement = new Achievement(1, "5 días de racha", "Descripción", "Daily", 10);
        Background background = new Background(1, "Primavera", "Descripción", 200);
        Inventory inventory = new Inventory(TEST_USER_ID, Arrays.asList(achievement), background);

        // Act
        Inventory savedInventory = inventoryRepository.save(inventory);
        Optional<Inventory> foundInventory = inventoryRepository.findById(savedInventory.getId());

        // Assert
        Assertions.assertThat(foundInventory).isPresent();
        Assertions.assertThat(foundInventory.get().getUserId()).isEqualTo(TEST_USER_ID);
        Assertions.assertThat(foundInventory.get().getBackgrounds()).hasSize(1);
        Assertions.assertThat(foundInventory.get().getAchievements()).hasSize(1);
    }

    @Test
    public void findByUserId_shouldReturnCorrectInventory() {
        // Arrange
        Achievement achievement = new Achievement(2, "10 días de racha", "Descripción", "Daily", 25);
        Background background = new Background(2, "Verano", "Descripción", 200);
        Inventory inventory = new Inventory(TEST_USER_ID, Arrays.asList(achievement), background);
        inventoryRepository.save(inventory);

        // Act
        Optional<Inventory> foundInventory = inventoryRepository.findByUserId(TEST_USER_ID);

        // Assert
        Assertions.assertThat(foundInventory).isPresent();
        Assertions.assertThat(foundInventory.get().getCoins()).isEqualTo(0);
        Assertions.assertThat(foundInventory.get().getAchievements().get(0).getId()).isEqualTo(2);
    }

    @Test
    public void updateInventoryCoins_shouldPersistChanges() {
        // Arrange
        Background background = new Background(3, "Invierno", "Descripción", 200);
        Inventory inventory = new Inventory(TEST_USER_ID, Arrays.asList(), background);
        Inventory savedInventory = inventoryRepository.save(inventory);

        // Act
        savedInventory.setCoins(150);
        inventoryRepository.save(savedInventory);
        Optional<Inventory> updatedInventory = inventoryRepository.findByUserId(TEST_USER_ID);

        // Assert
        Assertions.assertThat(updatedInventory).isPresent();
        Assertions.assertThat(updatedInventory.get().getCoins()).isEqualTo(150);
    }
}