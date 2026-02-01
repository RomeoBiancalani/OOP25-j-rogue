package it.unibo.rogue.entity.entities.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Enemy;

/**
 * Base implementation for all enemy entities.
 * <p>
 * Provides internal state and logic for all game enemies.
 * </p>
 */
public abstract class AbstractEnemy extends AbstractEntity implements Enemy{
    
    /**
     * The probability (1 out of 10) that an enemy spawns sleeping.
     */
    private static final int SLEEP_CHANCE = 10;

    private static final Random rand = new Random();

    private final int visibility;
    private boolean sleeping;

    /**
     * Constructos an AbstractEnemy with the specified attributes
     * 
     * @param currentPosition The current position of the enemy.
     * @param level The level of the enemy.
     * @param lifePoint The life points of the enemy.
     * @param armorClass The armor class of the enemy.
     * @param visibility The visibility range of the enemy.
     * @throws IllegalArgumentException if visibility range is negative.
     */
    public AbstractEnemy(final Position currentPosition,
                         final int level,
                         final int lifePoint,
                         final int armorClass,
                         final int visibility) {

            super(lifePoint, level, armorClass, currentPosition);
            if (visibility < 0) {
                throw new IllegalArgumentException("Visibility range cannot be negative");
            }
            this.sleeping = computeSleeping();
            this.visibility = visibility;
    }

    @Override
    public boolean isSleeping() {
        return sleeping;
    }

    @Override
    public void wakeUp() {
        sleeping = false;
    }

    @Override
    public boolean computeSleeping() {
        return rand.nextInt(SLEEP_CHANCE) == 0;
    }

    @Override
    public boolean canSeePlayer(Position playerPosition) {
        if (playerPosition == null) {
            return false;
        }
        return getVisiblePositions().stream().anyMatch(p -> p.equals(playerPosition));
    }

    /**
     * Gets the list of the position that are visible from
     * the enemy's current position.
     * 
     * @return A list of the visible positions.
     */
    protected List<Position> getVisiblePositions() {
        List<Position> visible = new LinkedList<>();
        Position currentPosition = super.getPosition();
        for (int j = currentPosition.y() - visibility; j <= currentPosition.y() + visibility; j++) {
            for (int i = currentPosition.x() - visibility; i <= currentPosition.x() + visibility; i++) {
                visible.add(new Position(i, j));
            }
        }
        return Collections.unmodifiableList(visible);
    }

    /**
     * Gets the random number generator use in this class.
     * 
     * @return The random number generator.
     */
    protected static Random getRandom() {
        return rand;
    }

    /**
     * Generates a random move.
     * 
     * @return A random move.
     */
    protected Move randomMove() {
        Move[] moves = Move.values();
        return moves[rand.nextInt(moves.length)];
    }

    /**
     * Determines the move direction towards the specified target position.
     * 
     * @param targetPosition The target position to move towards.
     * @return The move direction towards the target position.
     */
    protected Move moveToward(Position targetPosition) {
        if (targetPosition == null) {
            return Move.IDLE;
        }

        Position from = getPosition();
        int dx = Integer.compare(targetPosition.x(), from.x());
        int dy = Integer.compare(targetPosition.y(), from.y());

        for (Move move : Move.values()) {
            if (move.getX() == dx && move.getY() == dy) {
                return move;
            }
        }

        return Move.IDLE;
    }
}
