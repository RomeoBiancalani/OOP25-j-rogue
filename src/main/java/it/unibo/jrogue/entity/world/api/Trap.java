package it.unibo.jrogue.entity.world.api;

import it.unibo.jrogue.commons.Position;

/**
 * Represents a trap in the dungeon.
 */
public interface Trap {

    /**
     * Triggers the trap effect and disable the trap.
     */
    void trigger();

    /**
     * Returns the position of this trap.
     *
     * @return the trap position
     */
    Position getPosition();

    /**
     * Get a description of the trap.
     *
     * @return description of the trap
     * */

    String getDescription();
}
