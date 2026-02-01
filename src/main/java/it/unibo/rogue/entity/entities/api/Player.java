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
     */
    void equip(Equipment equipment);

    /**
     * Equip the specified armor for the player.
     * 
     * @param armor The armor to be equipped.
     */
    void equipArmor(Equipment armor);

    /**
     * Equip the specified weapon for the player.
     * 
     * @param weapon the weapon to be equipped.
     */
    void equipWeapon(Equipment weapon);

}
