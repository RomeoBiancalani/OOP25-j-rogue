package it.unibo.jrogue.entity.items.impl;

import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Consumable;

/**
 * Potion that if consumed heals
 * back some of the player health.
 */
public class HealthPotion implements Consumable {
    private static final int HEALING_AMOUNT = 20;
    private int consumableAmount;

    /**
     * {@inheritDoc}
     */
    @Override
    public void consume(final Player player) {
        if (consumableAmount > 0) {
            player.heal(HEALING_AMOUNT);
            consumableAmount--;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Healing potion, use it to regenerate a considerable amount of HP";
    }

    /**
     * Getter for item amount.
     *
     * @return consumableAmount.
     */

    public int getConsumableAmount() {
        return consumableAmount;
    }
}
