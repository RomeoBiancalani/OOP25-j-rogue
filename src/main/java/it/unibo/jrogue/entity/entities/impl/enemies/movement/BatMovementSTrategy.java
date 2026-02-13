package it.unibo.jrogue.entity.entities.impl.enemies.movement;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.entities.api.MovementStrategy;

/**
 * Class that rapresents a probabilitic behavior, 
 * entitiy has a specific percentage of chanche to move toward to the player.
 */
public class BatMovementSTrategy implements MovementStrategy {
    private final int chasePercentage;
    private final MovementStrategy chaseStrategy = new ChasingMovementStrategy();
    private final MovementStrategy randomStrategy = new RandomMovementStrategy();

    /**
     * Contruct a probabilitic chase strategy with a specific 
     * chanche to chase the player.
     * 
     * @param percentage The probability to move towards to the player.
     */
    public BatMovementSTrategy(final int percentage) {
        this.chasePercentage = percentage;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Move towards to the player with the specified probability
     * or move with a random behavior.
     * </p>
     */
    @Override
    public Move calculateNextMove(Position start, Position target) {
        if (GameRandom.nextInt(100) >= chasePercentage) {
            return randomStrategy.calculateNextMove(start, target);
        }
        return chaseStrategy.calculateNextMove(start, target);
    }

}
