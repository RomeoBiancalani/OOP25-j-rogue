package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.world.api.Trap;
import it.unibo.jrogue.entity.world.api.TrapFactory;

import java.util.Optional;

/**
 * Implementation of the TrapFactory.
 */
public final class TrapFactoryImpl implements TrapFactory {

    private static final int CHANCE_ROCK = 70;
    private static final int ROLL_MAX = 100;
    private static final int MIN_LEVEL_SPIKES = 6;

    @Override
    public Trap createRockTrap(final Position position) {
        return new RockTrap(position);
    }

    @Override
    public Trap createPitOfSpikesTrap(final Position position) {
        return new PitOfSpikesTrap(position);
    }

    @Override
    public Optional<Trap> createRandomTrap(final Position position, final int level) {
        final int roll = GameRandom.nextInt(ROLL_MAX);
              if (level >= MIN_LEVEL_SPIKES && roll >= CHANCE_ROCK) {
            return Optional.of(createPitOfSpikesTrap(position));
        }
        return Optional.of(createRockTrap(position));
    }
}
