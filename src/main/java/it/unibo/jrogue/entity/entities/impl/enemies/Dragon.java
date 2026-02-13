package it.unibo.jrogue.entity.entities.impl.enemies;

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
            D_VISIBILITY,
            new ChasingMovementStrategy()
        );
    }

    /**
     * {@inheritDoc}
     * <p>
     * Dragon attack: 3d8
     * </p>
     */
    @Override
    public int getAttack() {
        return Dice.roll(3, 8) + getLevel();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Dragon xp: 5d8
     * </p>
     */
    @Override
    protected int computeXpValue() {
        return Dice.roll(5, 8);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Dragon loot: rare object
     * </p>
     */
    @Override
    protected Optional<Item> generateLoot() {
        return new ItemFactoryImpl().createRandomItem(getLevel() + 2);
    }
}
