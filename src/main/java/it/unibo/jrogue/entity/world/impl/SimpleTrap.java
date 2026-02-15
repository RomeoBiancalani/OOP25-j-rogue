package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;

/**
 * Simple implementation of a dungeon trap.
 */
public final class SimpleTrap implements Trap {

    private final Position position;
    private final int damage;
    private boolean active;
    private boolean discovered;

    /**
     * Creates a new trap.
     *
     * @param position the trap position
     * @param damage the damage dealt when triggered
     */
    public SimpleTrap(final Position position, final int damage) {
        this.position = position;
        this.damage = damage;
        this.active = true;
        this.discovered = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void trigger() {
        if (active) {
            active = false;
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isDiscovered() {
        return discovered;
    }

    @Override
    public void discover() {
        discovered = true;
    }

    /**
     * Returns the damage this trap deals.
     *
     * @return the damage amount
     */
    public int getDamage() {
        return damage;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
    }
}
