package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;

/**
 * Simple rock trap.
 */

public final class RockTrap implements Trap {
    private final Position position;
    private boolean active;
    /**
     * Constructor.
     *
     * @param position which is the position in the map
     * */

    public RockTrap(final Position position) {
        this.position = position;
        this.active = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trigger() {
        if (active) {
            active = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getDescription() {
        return "You tripped over a rock";
    }
}
