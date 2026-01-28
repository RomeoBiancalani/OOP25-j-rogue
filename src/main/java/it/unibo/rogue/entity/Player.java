package it.unibo.rogue.entity;

import it.unibo.rogue.entity.items.Inventory;

public interface Player extends Entity {

    /**
     * 
     * @return the inventory of the player
     */
    Inventory getInventory();

}
