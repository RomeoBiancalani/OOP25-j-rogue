package it.unibo.rogue.entity.entities.impl;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Entity;

/**
 * Base implementation of the Entity interface.
 * 
 * <p>
 * Provides shared internal state and logic for all game entities.
 * </p>
 */
public abstract class AbstractEntity implements Entity {

    private static final int HP_INCREMENT_PER_LEVEL = 3;

    private int maxLifePoint;
    private int lifePoint;
    private int level;
    private final int armorClass;
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

        this.maxLifePoint = lifePoint;
        this.lifePoint = lifePoint;
        this.level = level;
        this.armorClass = armorClass;
        this.currentPosition = startPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return currentPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return lifePoint > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxLifePoint() {
        return maxLifePoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doMove(final Move move) {
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }

        currentPosition = move.applyToPosition(currentPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getArmorClass() {
        return armorClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void heal(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Heal amount cannot be negative");
        }
        lifePoint = Math.min(this.lifePoint + amount, maxLifePoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void damage(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Heal amount cannot be negative");
        }
        lifePoint = lifePoint - amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void levelUp() {
        this.level++;
        this.maxLifePoint += HP_INCREMENT_PER_LEVEL;
        this.lifePoint += HP_INCREMENT_PER_LEVEL;
    }

}
