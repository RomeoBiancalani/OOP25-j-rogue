package it.unibo.jrogue.entity.items.impl;

import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Consumable;

/**
 * Class to implement the food item.
 */
public class Food implements Consumable {
    private static final int HEALING_AMOUNT = 5;
    private int consumableAmount;

    @Override
    public void consume(Player player) {
        if (consumableAmount > 0) {
        player.heal(HEALING_AMOUNT);
        consumableAmount--;
        }
    }

    @Override
    public String getDescription() {
        return "Food have healing properties, get healed by: " + HEALING_AMOUNT + " HP";
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
