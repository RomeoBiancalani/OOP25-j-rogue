package it.unibo.jrogue.entity.entities.impl.enemies.factory;

import java.util.Optional;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.impl.enemies.Bat;
import it.unibo.jrogue.entity.entities.impl.enemies.Dragon;
import it.unibo.jrogue.entity.entities.impl.enemies.HobGoblin;

/**
 * Factory for enemies that manages scaling and level.
 */
public class EnemiesFactoryImpl implements EnemiesFactory{
    private static final int CHANCHE_BAT = 40;
    private static final int CHANCHE_GOBLIN = 5;
    private static final int ROLL_MAX = 100;

    private static final int HG_MIN_LEVEL = 2;
    private static final int DRAGON_MIN_LEVEL = 2;

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createBat(final Position position, final int level) {
        return new Bat(position, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createHobGoblin(final Position Position, final int level) {
        return new HobGoblin(Position, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createDragon(final Position Position, final int level) {
        return new Dragon(Position, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Enemy> createRandomEnemy(final Position position, final int level) {
        final int roll = GameRandom.nextInt(ROLL_MAX);
        if (level >= DRAGON_MIN_LEVEL && roll >= CHANCHE_GOBLIN) {
            return Optional.of(createDragon(position, level));
        }
        if (level >= HG_MIN_LEVEL && roll >= CHANCHE_BAT) {
            return Optional.of(createHobGoblin(position, level));
        }
        return Optional.of(createBat(position, level));
    }

}
