package com.zephiro.garden.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.garden.entity.Background;
import com.zephiro.garden.entity.Flower;
import com.zephiro.garden.service.InventoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> createInventory(@RequestBody String userId) {
        try {
            inventoryService.addInventory(userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Inventory created successfully\"}");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while adding flower\"}");
        }
    }
    
    @GetMapping("/{userId}/flowers")
    public ResponseEntity<?> getFlowers(@PathVariable String userId) {
        try {
            List<Flower> flowers = inventoryService.getFlowersOnInventory(userId);
            return ResponseEntity.ok(flowers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while retrieving flower\"}");
        }
    }

    @GetMapping("/{userId}/flower/{flowerId}")
    public ResponseEntity<?> getFlowerById(@PathVariable String userId, @PathVariable int flowerId) {
        try {
            Flower flower = inventoryService.getFlowerById(userId, flowerId);
            return ResponseEntity.ok(flower);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while retrieving flower\"}");
        }
    }

    @GetMapping("/{userId}/backgrounds")
    public ResponseEntity<?> getBackgrounds(@PathVariable String userId) {
        try {
            List<Background> backgrounds = inventoryService.getBackgrounds(userId);
            return ResponseEntity.ok(backgrounds);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while retrieving backgrounds\"}");
        }
    }

    @GetMapping("/{userId}/background/{backgroundId}")
    public ResponseEntity<?> getBackgroundById(@PathVariable String userId, @PathVariable int backgroundId) {
        try {
            Background background = inventoryService.getBackgroundById(userId, backgroundId);
            return ResponseEntity.ok(background);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while retrieving background\"}");
        }
    }

    @GetMapping("/{userId}/coins")
    public ResponseEntity<?> getUserCoins(@PathVariable String userId) {
        try {
            int coins = inventoryService.getUserCoins(userId);
            return ResponseEntity.ok(coins);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while retrieving coins\"}");
        }
    }

    @PutMapping("/{userId}/buyFlower")
    public ResponseEntity<?> buyFlower(@PathVariable String userId, @RequestBody Flower flower) {
        try {
            inventoryService.buyFlower(userId, flower);
            return ResponseEntity.ok("{\"message\": \"Flower bought successfully\"}");
        } catch (RuntimeException e) {
            if(e.getMessage().contains("not found"))
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
            else
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while buying flower\"}");
        }
    }

    @PutMapping("/{userId}/buyBackground")
    public ResponseEntity<?> buyBackground(@PathVariable String userId, @RequestBody Background background) {
        try {
            inventoryService.buyBackground(userId, background);
            return ResponseEntity.ok("{\"message\": \"Background bought successfully\"}");
        } catch (RuntimeException e) {
            if(e.getMessage().contains("not found"))
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
            else
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while buying background\"}");
        }
    }

    @PutMapping("/{userId}/rewardEmergencyContact")
    public ResponseEntity<?> rewardEmergencyContact(@PathVariable String userId) {
        try {
            inventoryService.rewardEmergencyContact(userId);
            return ResponseEntity.ok("{\"message\": \"Achievement rewarded successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while rewarding emergency contact\"}");
        }
    }

    @PutMapping("/{userId}/rewardStreak/{streak}")
    public ResponseEntity<?> rewardStreak(@PathVariable String userId, @PathVariable int streak) {
        try {
            inventoryService.rewardStreak(userId, streak);
            return ResponseEntity.ok("{\"message\": \"Streak rewarded successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while rewarding streak\"}");
        }
    }

    @PutMapping("/{userId}/rewardSurvey")
    public ResponseEntity<?> rewardSurvey(@PathVariable String userId) {
        try {
            inventoryService.rewardSurvey(userId);
            return ResponseEntity.ok("{\"message\": \"Survey rewarded successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while rewarding survey\"}");
        }
    }

    @PutMapping("/{userId}/rewardContent")
    public ResponseEntity<?> rewardContent(@PathVariable String userId) {
        try {
            inventoryService.rewardContent(userId);
            return ResponseEntity.ok("{\"message\": \"Content rewarded successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while rewarding content\"}");
        }
    }

    //* Development team only 
    @PutMapping("/dailyReset")
    public ResponseEntity<?> dailyReset() {
        try {
            inventoryService.dailyReset();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"message\": \"Daily reset completed successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred during daily reset\"}");
        }
    }
}
