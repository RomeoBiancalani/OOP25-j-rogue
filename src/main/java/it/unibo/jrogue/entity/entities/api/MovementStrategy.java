package it.unibo.jrogue.entity.entities.api;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;

/**
 * Interface for enemy movement behavior.
 */
@FunctionalInterface
public interface MovementStrategy {
    /**
     * Determines the next move.
     * 
     * @param start The start position of the entity.
     * @param target The position of the player.
     * @return The calculated move.
     */
    Move calculateNextMove(Position start, Position target);
}
