package com.zephiro.garden.entity;

public class Flower {
    
    private int id;
    private String name;
    private String description;
    private int price;
    private String healthyAsset;
    private String dryAsset;

    public Flower() {
    }

    public Flower(int id, String name, String description, int price, String healthyAsset, String dryAsset) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.healthyAsset = healthyAsset;
        this.dryAsset = dryAsset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHealthyAsset() {
        return healthyAsset;
    }

    public void setHealthyAsset(String healthyAsset) {
        this.healthyAsset = healthyAsset;
    }

    public String getDryAsset() {
        return dryAsset;
    }

    public void setDryAsset(String dryAsset) {
        this.dryAsset = dryAsset;
    }
}
