package it.unibo.jrogue.entity.entities.impl.enemies.movement;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.MovementStrategy;

/**
 * Class that rapresents a standard behavior, the entity moves towards to the
 * player.
 */
public class ChasingMovementStrategy implements MovementStrategy {

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Move towards to the player.
     * </p>
     */
    @Override
    public Move calculateNextMove(Position start, Position target) {
        final int dx = Integer.compare(target.x(), start.x());
        final int dy = Integer.compare(target.y(), start.y());

        for (final Move move : Move.values()) {
            if (move.getX() == dx && move.getY() == dy) {
                return move;
            }
        }
        return Move.IDLE;
    }

}
