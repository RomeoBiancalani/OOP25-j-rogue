package it.unibo.jrogue.entity.items.impl;

import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Consumable;

/**
 * Class to implement the food item.
 */
public class Food implements Consumable {
    private static final int HEALING_AMOUNT = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    public void consume(final Player player) {
            player.heal(HEALING_AMOUNT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Food have healing properties, get healed by: " + HEALING_AMOUNT + " HP";
    }

}
