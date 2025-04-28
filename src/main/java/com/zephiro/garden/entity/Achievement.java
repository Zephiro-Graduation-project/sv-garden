package com.zephiro.garden.entity;

public class Achievement {

    private int id;
    private String title;
    private String description;
    private String type; // daily or one time
    private int reward;
    private boolean completed;

    public Achievement() {
    }

    public Achievement(int id, String title, String description, String type, int reward) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.reward = reward;
        this.completed = false; // Initialize completed to false
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
