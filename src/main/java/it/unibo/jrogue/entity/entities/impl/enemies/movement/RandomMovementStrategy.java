package it.unibo.jrogue.entity.entities.impl.enemies.movement;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.entities.api.MovementStrategy;

/**
 * Class that represents a random behavior.
 */
public class RandomMovementStrategy implements MovementStrategy {
    /**
     * {@inheritDoc}
     */
    @Override
    public Move calculateNextMove(final Position start, final Position target) {
        Move[] moves = Move.values();
        return moves[GameRandom.nextInt(moves.length)];
    }
}
