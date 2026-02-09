package it.unibo.rogue.entity.entities.api;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;

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
     * Reutrn the maximum LifePoint of the entity.
     * 
     * @return the maximum LifePoint of the entity.
     */
    int getMaxLifePoint();

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
     * @throws NullPointerException if move is null.
     */
    void doMove(Move move);

    /**
     * Heal the entity of the specified amount.
     * 
     * @param amount The amount of life point to heal.
     * @throws IllegalArgumentException if amount is negative.
     */
    void heal(int amount);

    /**
     * Damage the entity of the specified amount.
     * 
     * @param amount The amount of damge to deal.
     * @throws IllegalArgumentException if amount is negative.
     */
    void damage(int amount);

    /**
     * Increment the level of the entity.
     */
    void levelUp();

}
