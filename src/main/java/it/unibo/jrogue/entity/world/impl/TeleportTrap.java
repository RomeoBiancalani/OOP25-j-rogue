package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;
import it.unibo.jrogue.controller.DungeonController;

/**
 * Class that implements the trap interface.
 */
public class TeleportTrap implements Trap {

    private final Position position;
    private boolean active;
    private boolean discovered;

    public TeleportTrap(final Position position) {
        this.position = position;
        this.active = true;
        this.discovered = false;
    }

    // Nota: per ora facciamo che teleportBack chiama trigger e non viceversa a
    // causa del controller.
    public void teleportBack(final DungeonController controller) {
        if (!this.active) {
            return;
        }
        controller.previousLevel();
        this.trigger();
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
    public void discover() {
        discovered = true;
    }

    /**
     * {@inheriDoc}
     */
    @Override
    public boolean isDiscovered() {
        return discovered;
    }

}
