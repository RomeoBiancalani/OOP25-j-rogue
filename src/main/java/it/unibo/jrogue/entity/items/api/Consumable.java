package it.unibo.jrogue.entity.items.api;

import it.unibo.jrogue.entity.entities.api.Player;

/**
 * Represents an item that can be consumed by the player.
 * Once used, the item is removed from the inventory and 
 * the player gains a benefit (regains some health or
 * uses a scrolls).
 */
public interface Consumable extends Item {

    /**
     * Consumes the item.
     * 
     * @param player the player who consumes the item.
     */
    void consume(Player player);
}
