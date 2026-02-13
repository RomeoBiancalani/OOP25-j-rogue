package it.unibo.jrogue.entity.entities.impl.enemies;

import it.unibo.jrogue.commons.Dice;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.impl.AbstractEnemy;

/**
 * Rapresent a Dragon enemy.
 * 
 * <p>
 * Dragon are power enemies (high HP, high damage)
 * with standard behavior. 
 * If they can see the player they move towards to it.
 * </p>
 */
public class Dragon extends AbstractEnemy {

    private static final int D_LEVEL = 6;
    private static final int D_AC = 12;
    private static final int D_VISIBILITY = 5;
    private static final int D_NUM_DICE = 4;
    private static final int D_SIDES_DICE = 10;

    /**
     * Construct a new Dragon at the specified starting position.
     * Initializes base stats (level, HP, AC, visibility).
     * 
     * @param startPosition The initial position of the hobgoblin.
     */
    public Dragon(final Position startPosition) {
        super(
            startPosition, 
            D_LEVEL, 
            Dice.roll(D_NUM_DICE, D_SIDES_DICE), 
            D_AC, 
            D_VISIBILITY
        );
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Dragon behavior: If it can see the player, it move towards to it.
     * </p>
     */
    @Override
    public Move getNextMove(final Position playerPosition) {
        if (isSleeping() || !canSeePlayer(playerPosition)) {
            return Move.IDLE;
        }
        return moveToward(playerPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttack() {
        return Dice.roll(3, 10) + getLevel();
    }
}
