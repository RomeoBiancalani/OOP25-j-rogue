package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;
/**
 * Trap which deals major damage.
 * */

public final class PitOfSpikesTrap implements Trap {
    private final Position position;
    private boolean active;
    /**
     * Constructor.
     *
     * @param position which is the position in the map
     * */

    public PitOfSpikesTrap(final Position position) {
        this.position = position;
        this.active = true;
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
    public String getDescription() {
        return "You fell on a trap";
    }
}
