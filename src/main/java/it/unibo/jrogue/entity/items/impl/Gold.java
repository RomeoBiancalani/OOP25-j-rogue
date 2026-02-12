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
            throw new IllegalArgumentException("la quantità dell'oro non può essere negativa o 0");
        }
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Sacchetto di monete d'oro ( " + amount + ")";
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
