package it.unibo.jrogue.entity.items.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.jrogue.entity.items.api.Inventory;
import it.unibo.jrogue.entity.items.api.Item;

/**
 * A simple implementation of Inventory using a HashMap.
 */
public final class SimpleInventory implements Inventory {

    private final Map<Integer, Item> inventory = new HashMap<>();
    private final int size;

    /**
     * Creates a new inventory with the specified capacity.
     *
     * @param size the maximum number of items
     */
    public SimpleInventory(final int size) {
        this.size = size;
    }

    @Override
    public boolean isFull() {
        return inventory.size() >= size;
    }

    @Override
    public Optional<Item> getItem(final int index) {
        return Optional.ofNullable(inventory.get(index));
    }

    @Override
    public boolean addItem(final Item item) {
        if (isFull()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!inventory.containsKey(i)) {
                inventory.put(i, item);
                return true;
            }
        }
        return false;
    }
}
