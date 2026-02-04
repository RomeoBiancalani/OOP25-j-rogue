package it.unibo.rogue.entity.items.api;

/**
 * Represent an item that the player can find and collect
 * from the dungeon floor.
 * 
 */
@FunctionalInterface
public interface Item {

    /**
     * Provides a description of the item.
     * 
     * @return a string containing the item description.
     */
    String getDescription();
}
