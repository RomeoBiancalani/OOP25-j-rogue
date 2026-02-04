package it.unibo.jrogue.items.api;

import java.util.Optional;

public interface ItemFactory {
    Item createStartingWeapon();
    Optional<Item> createRandomItem(int level);
    Item createRandomArmor(int level);
    }
