package it.unibo.rogue.entity;

public interface Enemy {

    /**
     * 
     * @return true wheter the enemy is sleeping, false otherwise
     */
    boolean isSleeping();

    /**
     * 
     * @return the next move for this enemy
     */
    Move getNextMove();

}
