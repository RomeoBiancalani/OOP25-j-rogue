package it.unibo.jrogue.entity.entities.impl.enemies;

import java.util.Optional;

import it.unibo.jrogue.commons.Dice;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.impl.AbstractEnemy;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.items.impl.ItemFactoryImpl;

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
    private static final int HG_AC = 6;
    private static final int HG_VISIBILITY = 2;
    private static final int HP_NUM_DICE = 2;
    private static final int HP_SIDES_DICE = 9;
    private static final int ATK_NUM_DICE = 2;
    private static final int ATK_SIDES_DICE = 5;

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
        if (isSleeping() || !canSeePlayer(playerPosition)) {
            return Move.IDLE;
        }
        return moveToward(playerPosition);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * HobGoblin attack: 2d5.
     * </p>
     */
    @Override
    public int getAttack() {
        return Dice.roll(ATK_NUM_DICE, ATK_SIDES_DICE) + getLevel();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * HobGoblin xp: 2d4.
     * </p>
     */
    @Override
    protected int computeXpValue() {
        return Dice.roll(2, 4);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * HobGoblin loot: mid item.
     * </p>
     */
    @Override
    protected Optional<Item> generateLoot() {
        return new ItemFactoryImpl().createRandomItem(getLevel() + 1);
    }
}
