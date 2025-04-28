package com.zephiro.garden.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.garden.entity.Achievement;
import com.zephiro.garden.entity.Background;
import com.zephiro.garden.entity.Flower;
import com.zephiro.garden.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/flowers")
    public ResponseEntity<?> getFlowers() {
        try {
            List<Flower> flowers = shopService.getFlowers();
            return ResponseEntity.ok(flowers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/backgrounds")
    public ResponseEntity<?> getBackgrounds() {
        try {
            List<Background> backgrounds = shopService.getBackgrounds();
            return ResponseEntity.ok(backgrounds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/achievements")
    public ResponseEntity<?> getAchievements() {
        try {
            List<Achievement> achievements = shopService.getAchievements();
            return ResponseEntity.ok(achievements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/flowers/{id}")
    public ResponseEntity<?> getFlowerById(@PathVariable int id) {
        try {
            Flower flower = shopService.getFlowerById(id);
            return ResponseEntity.ok(flower);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/backgrounds/{id}")
    public ResponseEntity<?> getBackgroundById(@PathVariable int id) {
        try {
            Background background = shopService.getBackgroundById(id);
            return ResponseEntity.ok(background);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }
}
