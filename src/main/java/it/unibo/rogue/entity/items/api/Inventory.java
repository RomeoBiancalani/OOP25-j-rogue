package it.unibo.rogue.entity.items.api;

import java.util.Optional;

/**
 * Represents a container for items with limited capacity.
 */
public interface Inventory {

    /**
     * Checks if the inventory is full.
     *
     * @return true if no more items can be added
     */
    boolean isFull();

    /**
     * Gets the item at the specified index.
     *
     * @param index the inventory slot index
     * @return the item at that index, or empty if none
     */
    Optional<Item> getItem(int index);

    /**
     * Adds an item to the inventory.
     *
     * @param item the item to add
     * @return true if the item was added successfully
     */
    boolean addItem(Item item);
}
