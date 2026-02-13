package it.unibo.jrogue.entity.entities.api;

import it.unibo.jrogue.entity.items.api.Equipment;
import it.unibo.jrogue.entity.items.api.Inventory;

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
     * Returns the gold amount of the player.
     * 
     * @return the gold of the player.
     */
    int getGold();

    /**
     * Collect the specified gold amount of the player.
     * 
     * @param amount The amount of gold to collect.
     */
    void collectGold(int amount);

    /**
     * Check if the specified equipment is equipped.
     * 
     * @param equipment The equipment to check.
     * @return true if the equipment is equipped, false otherwise.
     */
    boolean isEquipped(Equipment equipment);

    /**
     * Equip the specified equipment for the player.
     * 
     * @param equipment The equipment to be equipped.
     */
    void equip(Equipment equipment);

    /**
     * Unequip the specified equipment for the player.
     * 
     * @param equipment The equipment to be removed.
     */
    void remove(Equipment equipment);

    /**
     * Collect the specified amount of xp and checks for level up.
     * 
     * @param amount the amount of xp to collect.
     */
    void collectXP(int amount);

    /**
     * Check if the player has an armor equipped.
     * 
     * @return true if the player has an armor equipped.
     */
    boolean hasArmor();

}
