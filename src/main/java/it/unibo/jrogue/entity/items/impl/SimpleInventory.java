package it.unibo.jrogue.entity.items.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.jrogue.entity.items.api.Inventory;
import it.unibo.jrogue.entity.items.api.Item;

/**
 * Implementation of the Inventory.
 */
public class SimpleInventory implements Inventory {

    private final Map<Integer, Item> inventory = new HashMap<>();
    private final int size;

    /**
     * Constructor of the SimpleInventory.
     * 
     * @param size size of the Inventory.
     */
    public SimpleInventory(final int size) {
        if (size <= 0 || size >= 100) {
            throw new IllegalArgumentException("L'inventario deve avere dimensione positiva");
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
            throw new IllegalArgumentException("L'indice va fuori dai limiti dell'inventario");
        }
        return Optional.ofNullable(inventory.get(index));
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void addItem(final Item item) {
        if (item == null) {
            throw new NullPointerException("L'oggetto non può essere null");
        }

        if (isFull()) {
            throw new IllegalStateException("L'inventario è pieno non si può aggiungere altro");

        }
        for (int i = 0; i < size; i++) {
            if (!inventory.containsKey(i)) {
                inventory.put(i, item);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void removeItem(int index) {
        if (inventory.containsKey(index)) {
            inventory.remove(index);
        }
    }

}
