package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;

/**
 * Trap which deals major damage.
 */

public final class PitOfSpikesTrap implements Trap {
    private final Position position;
    private boolean active;

    /**
     * Constructor.
     *
     * @param position which is the position in the map
     */

    public PitOfSpikesTrap(final Position position) {
        this.position = position;
        this.active = true;
    }

    /**
     * {@inhertiDoc}
     */
    @Override
    public void trigger() {
        if (this.active) {

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
        return "You fell on a trap and hurt yourself";
    }
}
