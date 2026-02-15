package it.unibo.jrogue.entity.entities.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.jrogue.commons.Dice;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.api.MovementStrategy;
import it.unibo.jrogue.entity.items.api.Item;

/**
 * Base implementation for all enemy entities.
 * 
 * <p>
 * Provides internal state and logic for all game enemies.
 * </p>
 */
public abstract class AbstractEnemy extends AbstractEntity implements Enemy {

    /**
     * The probability (1 out of 10) that an enemy spawns sleeping.
     */
    private static final int SLEEP_CHANCE = 10;

    private final MovementStrategy movementStrategy;
    private final int visibility;
    private boolean sleeping;

    /**
     * Constructos an AbstractEnemy with the specified attributes.
     * 
     * @param currentPosition The current position of the enemy.
     * @param level           The level of the enemy.
     * @param lifePoint       The life points of the enemy.
     * @param armorClass      The armor class of the enemy.
     * @param visibility      The visibility range of the enemy.
     * @param strategy        The strategy of movement of the enemy
     * @throws IllegalArgumentException if visibility range is negative.
     * @throws NullPointerException if strategy is null.
     */
    public AbstractEnemy(final Position currentPosition,
            final int level,
            final int lifePoint,
            final int armorClass,
            final int visibility,
            final MovementStrategy strategy) {

        super(lifePoint, level, armorClass, currentPosition);
        if (visibility < 0) {
            throw new IllegalArgumentException("Visibility range cannot be negative");
        }
        Objects.requireNonNull(strategy);
        this.visibility = visibility;
        this.movementStrategy = strategy;
        this.sleeping = computeSleeping();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSleeping() {
        return sleeping;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void wakeUp() {
        sleeping = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean computeSleeping() {
        return GameRandom.nextInt(SLEEP_CHANCE) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move getNextMove(final Position playerPosition) {
        if (isSleeping()) {
            return Move.IDLE;
        }
        if (canSeePlayer(playerPosition)) {
            return movementStrategy.calculateNextMove(this.getPosition(), playerPosition);
        }
        return Move.IDLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSeePlayer(final Position playerPosition) {
        return playerPosition != null && getVisiblePositions().stream().anyMatch(p -> p.equals(playerPosition));
    }

    /**
     * Gets the list of the position that are visible from
     * the enemy's current position.
     * 
     * @return A list of the visible positions.
     */
    protected List<Position> getVisiblePositions() {
        final List<Position> visible = new LinkedList<>();
        final Position currentPosition = super.getPosition();
        for (int j = currentPosition.y() - visibility; j <= currentPosition.y() + visibility; j++) {
            for (int i = currentPosition.x() - visibility; i <= currentPosition.x() + visibility; i++) {
                visible.add(new Position(i, j));
            }
        }
        return Collections.unmodifiableList(visible);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if the enemy is alive.
     */
    @Override
    public int getXpDrop() {
        if (isAlive()) {
            throw new IllegalStateException("An alive enemy can't drop xp");
        }
        return Dice.roll(getLevel(), 4);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if the enemy is still alive.
     */
    @Override
    public final Optional<Item> getItemDrop() {
        if (isAlive()) {
            throw new IllegalStateException("An alive enemy can't drop item");
        }
        return generateLoot();
    }

    /**
     * Calculate specific xp to drop.
     * 
     * @return xp to drop.
     */
    protected abstract int computeXpValue();

    /**
     * Generate specific loot of the enemy.
     * 
     * @return The item to drop.
     */
    protected abstract Optional<Item> generateLoot();
}
