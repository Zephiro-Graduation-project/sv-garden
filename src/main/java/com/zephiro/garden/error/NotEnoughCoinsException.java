package com.zephiro.garden.error;

public class NotEnoughCoinsException extends RuntimeException {
    public NotEnoughCoinsException(String userId) {
        super("User with the id: " + userId + " does not have enough coins to buy the flower");
    }
}