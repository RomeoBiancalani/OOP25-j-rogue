package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;

/**
 * Simple rock trap.
 */

public class RockTrap implements Trap {
    private final Position position;
    private final int damage;
    private boolean active;

    /**
     * Constructor.
     *
     * @param position which is the position in the map
     */

    public RockTrap(final Position position) {
        this.position = position;
        this.active = true;
        this.damage = 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trigger() {
        if (this.active) {
            getDamageTrap();
            this.active = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "You tripped over a rock and lost" + getDamageTrap();
    }

    /**
     * Method that provides with the damage of the trap.
     * 
     * @return the damage of the trap.
     */
    public int getDamageTrap() {
        return damage;
    }
}
