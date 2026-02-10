package it.unibo.rogue.entity.items.api;

import it.unibo.rogue.entity.entities.api.Player;

/**
 * Represent an item that can be equipped by the player.
 * Once equipped, the stats of the item are added to the ones
 * of the player.
 */
public interface Equipment extends Item {

    /**
     * Equips the specified item.
     * 
     * @param player the player who equips the item.
     */
    void equip(Player player);

    /**
     * Unequip the specified item.
     * 
     * @param player the player who unequips the item.
     */
    void unequip(Player player);

    /**
     * Get the bonus of the specified item.
     * 
     * @return bonus of the equipment.
     */
    int getBonus();
}
