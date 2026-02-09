package it.unibo.jrogue.entity.world.api;

import it.unibo.jrogue.commons.Position;

/**
 * Represents a trap in the dungeon.
 */
public interface Trap {

    /**
     * Checks if the trap is still active.
     *
     * @return true if the trap is active
     */
    boolean isActive();

    /**
     * Triggers the trap effect.
     */
    void trigger();

    /**
     * Returns the position of this trap.
     *
     * @return the trap position
     */
    Position getPosition();

    /**
     * Checks if the trap has been discovered by the player.
     *
     * @return true if the trap is discovered
     */
    boolean isDiscovered();

    /**
     * Marks the trap as discovered.
     */
    void discover();
}
