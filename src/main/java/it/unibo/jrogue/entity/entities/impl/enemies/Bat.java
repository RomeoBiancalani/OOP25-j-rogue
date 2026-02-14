package it.unibo.jrogue.entity.entities.impl.enemies;

import java.util.Objects;
import java.util.Optional;
import it.unibo.jrogue.commons.Dice;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.impl.AbstractEnemy;
import it.unibo.jrogue.entity.entities.impl.enemies.movement.BatMovementSTrategy;
import it.unibo.jrogue.entity.items.api.Item;

/**
 * Rapresents a Bat enemy entity.
 * 
 * <p>
 * Bats are weak (low HP, low damage) but unpredictable enemies,
 * they follows a probabilistic movement pattern:
 * </p>
 */
public class Bat extends AbstractEnemy {

    private static final int CHASE_PLAYER_PERCENT = 60;

    private static final int BAT_BASE_LEVEL = 1;
    private static final int BAT_AC = 3;
    private static final int AC_SCALING = 3;
    private static final int BAT_VISIBILITY = 6;
    private static final int HP_NUM_DICE = 1;
    private static final int HP_SIDES_DICE = 8;
    private static final int ATK_NUM_DICE = 1;
    private static final int ATK_SIDES_DICE = 5;
    private static final int ATK_SCALING = 2;
    private static final int XP_NUM_DICE = 1;
    private static final int XP_SIDES_DICE = 3;

    /**
     * Construct a new Bat at the specified starting position,
     * with stats scaled by dungeon level.
     * 
     * @param startPosition The initial position of the bat.
     * @param level The current dungeon level.
     * @throws NullPointerException if start position is null.
     */
    public Bat(final Position startPosition, final int level) {
        super(
            Objects.requireNonNull(startPosition), 
            level, 
            Dice.roll(HP_NUM_DICE, HP_SIDES_DICE), 
            BAT_AC + (level / AC_SCALING), 
            BAT_VISIBILITY,
            new BatMovementSTrategy(CHASE_PLAYER_PERCENT)
        );
    }

    /**
     * Construct a new Bat at the specified starting position,
     * with base stats
     * 
     * @param startPosition The initial position of the bat.
     * @throws NullPointerException if start position is null.
     */
    public Bat(final Position startPosition) {
        super(
            Objects.requireNonNull(startPosition), 
            BAT_BASE_LEVEL, 
            Dice.roll(HP_NUM_DICE, HP_SIDES_DICE), 
            BAT_AC, 
            BAT_VISIBILITY,
            new BatMovementSTrategy(CHASE_PLAYER_PERCENT)
        );
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Bat attack: 1d4
     * </p>
     */
    @Override
    public int getAttack() {
        return Dice.roll(ATK_NUM_DICE, ATK_SIDES_DICE + (getLevel() / ATK_SCALING)) + getLevel();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Bat xp: 1d3
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
     * Bat loot: bat has no loot
     * </p>
     */
    @Override
    protected Optional<Item> generateLoot() {
        return Optional.empty();
    }

}
