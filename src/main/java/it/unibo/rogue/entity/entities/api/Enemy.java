package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;

/**
 * Defines the contract for all enemy entities in the game.
 */
public interface Enemy extends Entity {

    /**
     * Check whether the enemy is currently sleeping.
     * 
     * @return true if the enemy is sleeping, false otherwise.
     */
    boolean isSleeping();

    /**
     * Wake up the enemy from its sleeping state.
     */
    void wakeUp();

    /**
     * Determines the next move for this enemy based on the player's position.
     * 
     * @param playerPosition The current position of the player.
     * @return the next move for this enemy.
     */
    Move getNextMove(Position playerPosition);

    /**
     * Computes whether the enemy should be sleeping.
     * 
     * @return true if the enemy should be sleeping, false otherwise.
     */
    boolean computeSleeping();

    /**
     * Checks whether the enemy can see the player based on the player's position.
     * 
     * @param playerPosition The current position of the player.
     * @return true if the enemy can see the player, false otherwise.
     */
    boolean canSeePlayer(Position playerPosition);
}
