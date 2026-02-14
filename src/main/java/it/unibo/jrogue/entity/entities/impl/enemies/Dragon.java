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
    private static final int HP_NUM_DICE = 4;
    private static final int HP_SIDES_DICE = 10;
    private static final int ATK_NUM_DICE = 3;
    private static final int ATK_SIDES_DICE = 8;
    private static final int XP_NUM_DICE = 5;
    private static final int XP_SIDES_DICE = 8;

    /**
     * Construct a new Dragon at the specified starting position,
     * with stats scaled by dungeon level.
     * 
     * @param startPosition The initial position of the dragon.
     * @param level The dungeon level.
     * @throws NullPointerException if start position is null.
     */
    public Dragon(final Position startPosition, final int level) {
        super(
            Objects.requireNonNull(startPosition), 
            level, 
            Dice.roll(HP_NUM_DICE, HP_SIDES_DICE), 
            D_AC, 
            D_VISIBILITY, 
            new ChasingMovementStrategy()
        );
    }

    /**
     * Construct a new Dragon at the specified starting position,
     * with base stats.
     * 
     * @param startPosition The initial position of the dragon.
     * @throws NullPointerException if start position is null.
     */
    public Dragon(final Position startPosition) {
        super(
            Objects.requireNonNull(startPosition), 
            D_LEVEL, 
            Dice.roll(HP_NUM_DICE, HP_SIDES_DICE), 
            D_AC, 
            D_VISIBILITY, 
            new ChasingMovementStrategy()
        );
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Dragon attack: 3d8
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
     * Dragon xp: 5d8
     * </p>
     */
    @Override
    protected int computeXpValue() {
        return Dice.roll(XP_NUM_DICE, XP_SIDES_DICE);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Dragon loot: rare object
     * </p>
     */
    @Override
    protected Optional<Item> generateLoot() {
        return new ItemFactoryImpl().createRandomItem(getLevel() + 2);
    }
}
