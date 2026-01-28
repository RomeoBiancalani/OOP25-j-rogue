package it.unibo.rogue.entity.items.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.rogue.entity.items.api.Inventory;
import it.unibo.rogue.entity.items.api.Item;

public class SimpleInventory implements Inventory{

    private final Map<Integer,Item> inventory = new HashMap<>();
    private final int size;
    public SimpleInventory(int size) {
        this.size = size;
    }

    @Override
    public boolean isFull() {
        return inventory.size() >= size;
    }

    @Override
    public Optional<Item> getItem(int index) {
        return Optional.ofNullable(inventory.get(index));
    }

    @Override
    public boolean addItem(Item item) {
        if(isFull()) {
            return false;
        }
        for(int i = 0; i < size; i++) {
            if(!inventory.containsKey(i)) {
            inventory.put(i, item);
            return true;   
            }
        }
        return false;    
    }
    
}
