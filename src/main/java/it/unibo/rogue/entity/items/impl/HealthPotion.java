package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.items.api.Consumable;

/**
 * Potion that if consumed heals
 * back some of the player health.
 */
public class HealthPotion implements Consumable {
    private final static int HEALING_AMOUNT = 20;
    private int consumableAmount;

    @Override
    public void consume(Player player) {
        if (consumableAmount > 0) {
            player.heal(HEALING_AMOUNT);
            consumableAmount--;
        }
    }

    @Override
    public String getDescription(){
        return "Healing potion, use it to regenerate a considerable amount of HP";
    }
    /**
     * Getter for item amount
     *
     * @return consumableAmount
     * */

    public int getConsumableAmount(){
        return consumableAmount;
    }
}

