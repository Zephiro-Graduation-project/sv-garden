package com.zephiro.garden.error;

public class ItemAlreadyOwnedException extends RuntimeException {
    public ItemAlreadyOwnedException(int itemId) {
        super("Item with id " + itemId + " is already in the inventory");
    }
}
