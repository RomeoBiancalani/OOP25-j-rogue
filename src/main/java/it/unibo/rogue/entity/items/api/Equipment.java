package it.unibo.rogue.entity.items.api;

import it.unibo.rogue.entity.entities.api.Player;

/**
 * Represent an item that can be equipped by the player.
 * Once equipped, the stats of the item are added to the ones
 * of the player.
 */
public interface Equipment extends Item {

    /**
     * Equips the item.
     * 
     * @param player the player who equips the item.
     */
    void equip(Player player);
}
