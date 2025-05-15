package com.zephiro.garden.repository;

import com.zephiro.garden.entity.Background;
import com.zephiro.garden.entity.Garden;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

@DataMongoTest
public class GardenRepositoryTest {

    private static final String TEST_USER_ID = "67fdcb6afa5d893f602f9b2d";

    @Autowired
    private GardenRepository gardenRepository;

    @Test
    public void saveGarden_shouldPersistWithCorrectFields() {
        // Arrange
        Background background = new Background(1, "Primavera", "Un jardín florecido", 200);
        Garden garden = new Garden(TEST_USER_ID, background);

        // Act
        Garden savedGarden = gardenRepository.save(garden);
        Optional<Garden> foundGarden = gardenRepository.findById(savedGarden.getId());

        // Assert
        Assertions.assertThat(foundGarden).isPresent();
        Assertions.assertThat(foundGarden.get().getUserId()).isEqualTo(TEST_USER_ID);
        Assertions.assertThat(foundGarden.get().getBackground().getId()).isEqualTo(1);
        Assertions.assertThat(foundGarden.get().getFlowers()).hasSize(12);
    }

    @Test
    public void findByUserId_shouldReturnCorrectGarden() {
        // Arrange
        Background background = new Background(2, "Verano", "Un paraíso con sol", 200);
        Garden garden = new Garden(TEST_USER_ID, background);
        gardenRepository.save(garden);

        // Act
        Optional<Garden> foundGarden = gardenRepository.findByUserId(TEST_USER_ID);

        // Assert
        Assertions.assertThat(foundGarden).isPresent();
        Assertions.assertThat(foundGarden.get().getBackground().getTitle()).isEqualTo("Verano");
    }

    @Test
    public void updateGardenState_shouldPersistChanges() {
        // Arrange
        Background background = new Background(3, "Invierno", "Un paisaje nevado", 200);
        Garden garden = new Garden(TEST_USER_ID, background);
        Garden savedGarden = gardenRepository.save(garden);

        // Act
        savedGarden.setState(true);
        gardenRepository.save(savedGarden);
        Optional<Garden> updatedGarden = gardenRepository.findByUserId(TEST_USER_ID);

        // Assert
        Assertions.assertThat(updatedGarden).isPresent();
        Assertions.assertThat(updatedGarden.get().isState()).isTrue();
    }
}