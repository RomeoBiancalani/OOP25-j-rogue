package it.unibo.jrogue.entity.items.api;

import java.util.Optional;

/**
 * Represents the factory that generates the items.
 * 
 */
public interface ItemFactory {

    /**
     * Creates the first weapon the player will have.
     * 
     * @return the first item created.
     */
    Item createStartingWeapon();

    /**
     * Creates a random item.
     * 
     * @param level the level used to scale the stats of the items.
     * 
     * @return an optional with the item created.
     */
    Optional<Item> createRandomItem(int level);

    /**
     * Creates a random Armor.
     * 
     * @param level the level used to scale the stats of the armor.
     * 
     * @return the armor created.
     */
    Item createRandomArmor(int level);
}
