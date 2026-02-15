package it.unibo.jrogue.entity.items.impl;

import it.unibo.jrogue.entity.items.api.Resources;

/**
 * Represents the gold coins in the game.
 */
public final class Gold implements Resources {
    private final int amount;

    /**
     * Constructor.
     * 
     * @param amount quantity of gold coins.
     */
    public Gold(final int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount of gold can not be negative or 0");
        }
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "A  sack with some gold :( " + amount + ")";
    }

    /**
     * Provides the amount of gold.
     * 
     * @return the amount of gold.
     */
    @Override
    public int getAmount() {
        return this.amount;
    }
}
