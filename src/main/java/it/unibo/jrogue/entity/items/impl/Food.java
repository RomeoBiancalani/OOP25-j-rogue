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
    public boolean consume(final Player player) {
        if (player.getLifePoint() == player.getMaxLifePoint()) {
            return false;
        } else {
            player.heal(HEALING_AMOUNT);
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Food have healing properties, get healed by: " + HEALING_AMOUNT + " HP";
    }

}
