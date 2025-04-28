package com.zephiro.garden.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gardens")
public class Garden {
    
    @Id
    private String id;
    private String userId;
    private boolean state; // true = wet, false = dry
    private Background background;
    private List<Flower> flowers;

    public Garden(String userId, Background background) {
        this.userId = userId;
        this.state = false;
        this.background = background;
        this.flowers = new ArrayList<>(Collections.nCopies(12, null));
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }
}
