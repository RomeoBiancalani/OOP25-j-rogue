package it.unibo.rogue.control.api;

import java.util.Optional;

import it.unibo.rogue.entity.items.api.Item;

/**
 * Inteface for the manager of the Inventory logic.
 */
public interface InventoryManager {

    /**
     * Provides with the item at the specific index.
     * 
     * @param index the index of the item.
     * 
     * @return an optional of the item.
     */
    Optional<Item> getItemAt(int index);

    /**
     * Method that uses the item.
     * 
     * @param index the index of the item.
     */
    void useItem(int index);

    /**
     * Method that returns the size of the inventory.
     * 
     * @return the size of the inventory.
     */
    int getSize();
}
