package it.unibo.jrogue.entity.entities.impl.enemies;

import java.util.Objects;
import java.util.Optional;

import it.unibo.jrogue.commons.Dice;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.impl.AbstractEnemy;
import it.unibo.jrogue.entity.entities.impl.enemies.movement.ChasingMovementStrategy;
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

    private static final int HG_BASE_LEVEL = 2;
    private static final int HG_AC = 6;
    private static final int AC_SCALING = 2;
    private static final int HG_VISIBILITY = 2;
    private static final int HP_NUM_DICE = 2;
    private static final int HP_SIDES_DICE = 9;
    private static final int ATK_NUM_DICE = 2;
    private static final int ATK_SIDES_DICE = 5;
    private static final int XP_NUM_DICE = 2;
    private static final int XP_SIDES_DICE = 4;

    /**
     * Construct a new HobGoblin at the specified starting position,
     * with stats scaled by dungeon level.
     * 
     * @param startPosition The initial position of the hobgoblin.
     * @param level The current dungeon level-
     * @throws NullPointerException if start position is null.
     */
    public HobGoblin(final Position startPosition, final int level) {
        super(
            Objects.requireNonNull(startPosition), 
            level, 
            Dice.roll(HP_NUM_DICE, HP_SIDES_DICE) + (level * 2), 
            HG_AC * (level / AC_SCALING),
            HG_VISIBILITY,
            new ChasingMovementStrategy()
        );
    }

    /**
     * Construct a new HobGoblin at the specified starting position,
     * with sbase stats.
     * 
     * @param startPosition The initial position of the hobgoblin.
     * @throws NullPointerException if start position is null.
     */
    public HobGoblin(final Position startPosition) {
        super(
            Objects.requireNonNull(startPosition), 
            HG_BASE_LEVEL, 
            Dice.roll(HP_NUM_DICE, HP_SIDES_DICE), 
            HG_AC,
            HG_VISIBILITY,
            new ChasingMovementStrategy()
        );
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
        return Dice.roll(XP_NUM_DICE, XP_SIDES_DICE) + (getLevel() * 2);
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
