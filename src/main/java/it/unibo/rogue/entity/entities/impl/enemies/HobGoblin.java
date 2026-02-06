package it.unibo.rogue.entity.entities.impl.enemies;

import it.unibo.rogue.commons.Dice;
import it.unibo.rogue.commons.Move;
import it.unibo.rogue.commons.Position;
import it.unibo.rogue.entity.entities.impl.AbstractEnemy;

/**
 * Rapresent a Hobgoblin enemy.
 * 
 * <p>
 * Hobgoblins are standard enemies (moderate HP, moderate damage)
 * with standard behavior. 
 * If they can see the player they move towards to it.
 * </p>
 */
public class HobGoblin extends AbstractEnemy {

    private static final int HG_LEVEL = 2;
    private static final int HG_AC = 11;
    private static final int HG_VISIBILITY = 2;
    private static final int HP_NUM_DICE = 2;
    private static final int HP_SIDES_DICE = 9;

    /**
     * Construct a new HobGoblin at the specified starting position.
     * Initializes base stats (level, HP, AC, visibility).
     * 
     * @param startPosition The initial position of the hobgoblin.
     */
    public HobGoblin(final Position startPosition) {
        super(
            startPosition, 
            HG_LEVEL, 
            Dice.roll(HP_NUM_DICE, HP_SIDES_DICE), 
            HG_AC, 
            HG_VISIBILITY
        );
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * HobGoblin behavior: If is awake and can see the player,
     * it move towards to it.
     * </p>
     */
    @Override
    public Move getNextMove(final Position playerPosition) {
        if (isSleeping()) {
            return Move.IDLE;
        }
        return moveToward(playerPosition);
    }
}
