package it.unibo.jrogue.entity.items.api;

/**
 * Represents a consumable item in the game.
 */
public interface Consumable extends Item {

    /**
     * Consumes this item, applying its effect.
     */
    void consume();
}
