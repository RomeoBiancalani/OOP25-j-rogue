package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.items.api.Consumable;

/**
 * Potion that if consumed heals
 * back some of the players health.
 */
public class HealthPotion implements Consumable {
    private static final int HEALING_AMOUNT = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Pozione rossa (Recupera " + HEALING_AMOUNT + "HP)";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consume(final Player player) {
        if (player != null) {
          return;
        } 
        //else {
        //player.heal(HEALING_AMOUNT);
        //System.out.println("Hai bevuto la pozione");
        //}
    }
}
