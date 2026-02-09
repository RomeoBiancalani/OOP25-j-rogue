package it.unibo.rogue.controller.api;

import it.unibo.rogue.commons.Move;

/**
 * MovementController is the main entry point for managing game turns.
 */
@FunctionalInterface
public interface MovementController {

    /**
     * Execute a single game turn base on the player's move.
     * 
     * @param move The moves that the player attempts.
     */
    void executeTurn(Move move);

}
