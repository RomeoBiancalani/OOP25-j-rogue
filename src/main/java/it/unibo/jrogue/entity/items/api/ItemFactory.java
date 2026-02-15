package it.unibo.jrogue.entity.items.api;

import java.util.Optional;

/**
 * Represents the factory that generates the items.
 * 
 */
public interface ItemFactory {

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

    /**
     * Creates a Health Potion.
     * 
     * @return th potion created.
     */
    Item createHealthPotion();

    /**
     * Creates Food.
     * 
     * @return the food item.
     */
    Item createFood();

    /**
     * Creates a random amount of gold.
     * 
     * @return the gold item.
     */
    Item createRandomGold();

    /**
     * Creates a Scroll.
     * 
     * @return the scroll created.
     */
    Item createScroll();

    /**
     * Creates a Ring with random healing power.
     * 
     * @return the ring created.
     */
    Item createRandomRing();

    /**
     * Creates a random weapon.
     * 
     * @param name  name of the weapon.
     * 
     * @param level level the player currently is.
     * 
     * @return a weapon.
     */
    Item createWeapon(String name, int level);

    /**
     * Creates the Amulet of Yendor.
     * 
     * @return the aamulet.
     */
    Item createAmulet();
}
