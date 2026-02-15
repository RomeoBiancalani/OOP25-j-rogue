package it.unibo.jrogue.entity.items.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.jrogue.entity.items.api.Inventory;
import it.unibo.jrogue.entity.items.api.Item;

/**
 * Implementation of the Inventory.
 */
public class SimpleInventory implements Inventory {
    private static final int MAX_ALLOWED_SIZE = 100;
    private final Map<Integer, Item> inventory = new HashMap<>();
    private final int size;

    /**
     * Constructor of the SimpleInventory.
     * 
     * @param size size of the Inventory.
     */
    public SimpleInventory(final int size) {
        if (size <= 0 || size >= MAX_ALLOWED_SIZE) {
            throw new IllegalArgumentException("The inventory size must be between 1 and " + MAX_ALLOWED_SIZE);
        }
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFull() {
        return inventory.size() >= size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Item> getItem(final int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("The index can not go outside the inventory limits");
        }
        return Optional.ofNullable(inventory.get(index));
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void addItem(final Item item) {
        Objects.requireNonNull(item, "Can not add a null item in the invenotry");

        if (isFull()) {
            throw new IllegalStateException("The inventory is full and can not be added more");

        }
        IntStream.range(0, size)
                .filter(i -> !inventory.containsKey(i))
                .findFirst()
                .ifPresent(i -> inventory.put(i, item));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeItem(final int index) {
        inventory.remove(index);
    }

}
