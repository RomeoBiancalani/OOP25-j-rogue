package it.unibo.rogue.entity.entities.impl;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Entity;

/**
 * Base implementation of the Entity interface.
 * <p>
 * Provides shared internal state and logic for all game entities.
 * </p>
 */
public abstract class AbstractEntity implements Entity{

    private int lifePoint;
    private final int level;
    private int armorClass;
    private Position currentPosition;

    /**
     * Construct an AbstractEntity with the specified attributes.
     * 
     * @param lifePoint The life points of the entity.
     * @param level The level of the entity.
     * @param armorClass The armor class of the entity.
     * @param startPosition The starting position of the entity.
     * @throws IllegalArgumentException if lifePoint or level isn't positive.
     * @throws IllegalArgumentException if startPosition is null.
     */
    public AbstractEntity(final int lifePoint,
                          final int level,
                          final int armorClass,
                          final Position startPosition) {

        if (lifePoint <= 0 || level <= 0) {
            throw new IllegalArgumentException("Life points and level must be positive");
        }
        if (startPosition == null) {
            throw new IllegalArgumentException("Starting position cannot be null");
        }

        this.lifePoint = lifePoint;
        this.level = level;
        this.armorClass = armorClass;
        this.currentPosition = startPosition;
    }

    @Override
    public Position getPosition() {
        return currentPosition;
    }

    @Override
    public boolean isAlive() {
        return lifePoint > 0;
    }

    @Override
    public int getLevel() {
        return level;
    }

    /**
     * @throws IllegalArgumentException if move is null.
     */
    @Override
    public void doMove(Move move) {
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }

        currentPosition = move.applyToPosition(currentPosition);
    }

    @Override
    public int getArmorClass() {
        return armorClass;
    }

}
