package com.zephiro.garden.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventories")
public class Inventory {
    
    @Id
    private String id;
    private String userId;
    private int coins;
    private int streak;
    private List<Background> backgrounds;
    private List<Flower> flowers;
    private List<Achievement> achievements;

    public Inventory() {
    }
    
    public Inventory(String userId, List<Achievement> achievements, Background background) {
        this.userId = userId;
        this.coins = 0;
        this.streak = 0;
        this.backgrounds = new ArrayList<>();
        this.backgrounds.add(background);
        this.flowers = new ArrayList<>();
        this.achievements = achievements;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public List<Background> getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(List<Background> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
}