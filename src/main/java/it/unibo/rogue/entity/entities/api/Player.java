package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.items.api.Equipment;
import it.unibo.rogue.entity.items.api.Inventory;

public interface Player extends Entity {

    /**
     * 
     * @return the inventory of the player
     */
    Inventory getInventory();

    int getHitBonus();

    void equip(Equipment equipment);

    void setArmor(Equipment armor);

    void setWeapon(Equipment weapon);

}
