package it.unibo.rogue.entity.entities.impl.Enemies;

import it.unibo.rogue.entity.Dice;
import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.impl.AbstractEnemy;

/**
 * Rapresent a Hobgoblin enemy.
 * <p>
 * Hobgoblins are standard enemies (moderate HP, moderate damage)
 * with standard behavior. 
 * If they can see the player they move towards to it.
 * </p>
 */
public class HobGoblin extends AbstractEnemy{

    /**
     * Construct a new HobGoblin at the specified starting position.
     * Initializes base stats (level, HP, AC, visibility).
     * 
     * @param startPosition The initial position of the hobgoblin.
     */
    public HobGoblin(Position starPosition) {
        super(starPosition, 2, Dice.roll(2, 9), 11, 2);
    }

    /**
     * {@inheritDoc}
     * <p>
     * HobGoblin behavior: If is awake and can see the player,
     * it move towards to it.
     * </p>
     */
    @Override
    public Move getNextMove(Position playerPosition) {
        if (isSleeping()) {
            return Move.IDLE;
        }
        return moveToward(playerPosition);
    }
}
