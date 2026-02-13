package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;

/**
 * Class that implements the trap inferface.
 */
public class RockTrap implements Trap {
    private final Position position;
    private final int damage;
    private boolean active;
    private boolean discovered;

    public RockTrap(final Position position) {
        this.position = position;
        this.active = true;
        this.damage = 3;
        this.discovered = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trigger() {
        this.active = false;
        this.discover();
    }

    /**
     * {@inheriDoc}
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDiscovered() {
        return discovered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discover() {
        this.discovered = true;
    }

    /**
     * Method that provides with the damage of the RockTrap.
     * 
     * @return the damage of the trap.
     */
    public int getDamage() {
        return damage;
    }
}
