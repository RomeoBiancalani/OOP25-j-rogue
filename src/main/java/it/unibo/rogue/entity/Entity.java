package it.unibo.rogue.entity;

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

}
