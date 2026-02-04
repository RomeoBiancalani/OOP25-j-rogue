package it.unibo.rogue.entity.items.api;

import java.util.Optional;

/**
 * Represents the inventory that the player has.
 */
public interface Inventory {

    /**
     * Checks if the inventory is full.
     * 
     * @return true if the inventory is full.
     */
    boolean isFull();

    /**
     *  Retrieves an item from the inventory by its index.
     * 
     * @param index the index of the item to retrieve.
     * 
     * @return an optional with the item if it exists.
     */
    Optional<Item> getItem(int index);

    /**
     * Adds an item to the inventory.
     * 
     * @param item the item that we want to add.
     * 
     * @return true if the item has been added.
     */
    boolean addItem(Item item);
}

