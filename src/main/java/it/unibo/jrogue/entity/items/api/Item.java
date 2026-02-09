package it.unibo.jrogue.entity.items.api;

/**
 * Represents an item in the game.
 */
@FunctionalInterface
public interface Item {

    /**
     * Returns a description of the item.
     *
     * @return the item description
     */
    String getDescription();
}
