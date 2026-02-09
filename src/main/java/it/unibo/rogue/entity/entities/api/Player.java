package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.items.api.Equipment;
import it.unibo.rogue.entity.items.api.Inventory;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;
import it.unibo.rogue.entity.items.impl.Ring;

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
     * Equip the specified armor for the player.
     * 
     * @param armor The armor to be equipped.
     */
    void equipArmor(Armor armor);

    /**
     * Equip the specified weapon for the player.
     * 
     * @param weapon the weapon to be equipped.
     */
    void equipWeapon(MeleeWeapon weapon);

    /**
     * Equip the specified ring for the player.
     * 
     * @param ring the ring to be equipped.
     */
    void equipRing(Ring ring);

    /**
     * Use the equipped ring.
     */
    void useRing();

    /**
     * Collect the specified amount of xp and checks for level up.
     * 
     * @param amount the amount of xp to collect.
     * @throws IllegalArgumentException if amount is negative
     */
    void collectXP(int amount);

}
