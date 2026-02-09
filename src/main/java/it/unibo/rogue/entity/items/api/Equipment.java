package it.unibo.rogue.entity.items.api;

/**
 * Represents an equippable item in the game.
 */
public interface Equipment extends Item {

    /**
     * Equips this item on the player.
     */
    void equip();
}
