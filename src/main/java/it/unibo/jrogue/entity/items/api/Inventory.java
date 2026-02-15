package it.unibo.jrogue.entity.items.api;

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
     * @return true if added, false if full.
     */
    boolean addItem(Item item);

    /**
     * Removes an item from the inventory.
     * 
     * @param index the index of the item to be removed.
     */
    void removeItem(int index);

    /**
     * Returns the size of the inventory.
     * 
     * @return returns the size of the inventory.
     */
    int getSize();
}

