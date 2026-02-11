package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.items.api.Consumable;

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
