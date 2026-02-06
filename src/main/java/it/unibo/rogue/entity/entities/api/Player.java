package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.items.api.Equipment;
import it.unibo.rogue.entity.items.api.Inventory;
import it.unibo.rogue.entity.items.api.Item;

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
     * @throws NullPointerException if the specified equipment is null.
     */
    void equip(Equipment equipment);

    /**
     * Equip the specified armor for the player.
     * 
     * @param armor The armor to be equipped.
     * @throws NullPointerException if the specified armor is null.
     */
    void equipArmor(Equipment armor);

    /**
     * Equip the specified weapon for the player.
     * 
     * @param weapon the weapon to be equipped.
     * @throws NullPointerException ff the specified weapon is null.
     */
    void equipWeapon(Equipment weapon);

    /**
     * PickUp the specified Item.
     * 
     * @param item The item to pick up.
     * @throws NullPointerException if the specified item is null.
     */
    void pickUpItem(Item item);

}
