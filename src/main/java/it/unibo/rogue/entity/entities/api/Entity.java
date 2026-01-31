package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;

public interface Entity {

    /**
     * 
     * @return the current position of the entity
     */
    Position getPosition();

    /**
     * 
     * @return true whether the entity is alive, false otherwise
     */
    boolean isAlive();

    /**
     * 
     * @return the current level of the entity
     */
    int getLevel();

    void doMove(Move move);

    int getArmorClass();

}
