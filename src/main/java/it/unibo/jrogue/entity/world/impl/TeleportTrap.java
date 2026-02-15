package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;
import it.unibo.jrogue.controller.DungeonController;

/**
 * Class that implements the teleport trap.
 */

public final class TeleportTrap implements Trap {

    private final Position position;
    private boolean active;
    /**
     * Constructor.
     *
     * @param position which is the position in the map
     * */

    public TeleportTrap(final Position position) {
        this.position = position;
        this.active = true;
    }

    // Nota: per ora facciamo che teleportBack chiama trigger e non viceversa a
    // causa del controller.
    /**
     * Method to call the generation of the previous level.
     *
     * @param controller is the controller to get back to the previous level
     * */

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

    @Override
    public String getDescription() {
           return "You fell on a teleport trap, you got teleported back by one level";
    }

}
