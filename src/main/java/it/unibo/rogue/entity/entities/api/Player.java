package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.items.api.Equipment;
import it.unibo.rogue.entity.items.api.Inventory;

/**
 * Represent the human-controlled protagonist in the game world.
 */
public interface Player extends Entity {

    /**
     * Returns the inventory of the player.
     * 
     * @return the inventory of the player.
     */
    Inventory getInventory();

    /**
     * Returns the hit bonus of the player.
     * 
     * @return the hit bonus of the player.
     */
    int getHitBonus();

    /**
     * Equip the specified equipment for the player.
     * 
     * @param equipment The equipment to be equipped.
     * @throws NullPointerException if equipment is null.
     */
    void equip(Equipment equipment);

    /**
     * Unequip the specified equipment for the player.
     * 
     * @param equipment The equipment to be removed.
     * @throws NullPointerException if equipment is null.
     */
    void remove(Equipment equipment);


    /**
     * Collect the specified amount of xp and checks for level up.
     * 
     * @param amount the amount of xp to collect.
     */
    void collectXP(int amount);

}
