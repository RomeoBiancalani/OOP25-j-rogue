package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;

public interface Enemy extends Entity{

    /**
     * 
     * @return true wheter the enemy is sleeping, false otherwise
     */
    boolean isSleeping();

    void wakeUp();

    /**
     * 
     * @return the next move for this enemy
     */
    Move getNextMove(Position playerPosition);
    
    boolean computeSleeping();

    boolean canSeePlayer(Position playerPosition);
}
