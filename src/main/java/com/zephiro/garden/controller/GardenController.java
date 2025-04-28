package com.zephiro.garden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.garden.entity.Flower;
import com.zephiro.garden.entity.Garden;
import com.zephiro.garden.service.GardenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/garden")
public class GardenController {
    
    @Autowired
    private GardenService gardenService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> createGarden(@PathVariable String userId) {
        try {
            gardenService.addGarden(userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Garden created successfully\"}");
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while creating garden\"}");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getGarden(@PathVariable String userId) {
        try {
            Garden garden = gardenService.getGarden(userId);
            return ResponseEntity.ok(garden);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while retrieving garden\"}");
        }
    }

    @GetMapping("/{userId}/flower/{flowerId}")
    public ResponseEntity<?> getFlowerById(@PathVariable String userId, @PathVariable int flowerId) {
        try {
            Flower flower = gardenService.getFlowerById(userId, flowerId);
            return ResponseEntity.ok(flower);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while retrieving flower\"}");
        }
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<?> updateGarden(@PathVariable String userId, @RequestBody Garden garden) {
        try {
            gardenService.updateGarden(userId, garden);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"message\": \"Garden updated successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while updating garden\"}");
        }
    }

    //* Development team only 
    @PutMapping("/dailyReset")
    public ResponseEntity<?> dailyReset() {
        try {
            gardenService.dailyReset();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"message\": \"Daily reset completed successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred during daily reset\"}");
        }
    }
}
