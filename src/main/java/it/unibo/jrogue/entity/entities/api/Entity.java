package it.unibo.jrogue.entity.entities.api;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;

/**
 * Represents any intteractive object taht exist in the game world.
 */
public interface Entity {

    /**
     * Returns the current position of the entity.
     * 
     * @return the current position of the entity.
     */
    Position getPosition();

    /**
     * Check whether the entity is still alive.
     * 
     * @return true if the entity is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Get the armor class of the entity.
     * 
     * @return the armor class of the entity.
     */
    int getArmorClass();

    /**
     * Returns the current level of the entity.
     * 
     * @return the current level of the entity.
     */
    int getLevel();

    /**
     * Moves the entity in the specified direction.
     * 
     * @param move the move that the entity will perform.
     */
    void doMove(Move move);

}
