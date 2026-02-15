package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.world.api.Trap;
import it.unibo.jrogue.entity.world.api.TrapFactory;

import java.util.Optional;

/**
 * Implementation of the TrapFactory.
 */

public class TrapFactoryImpl implements TrapFactory {
    private static final int ROLL_MAX = 100;
    private static final int CHANCE_ROCK = 50;
    private static final int CHANCE_SPIKES = 80;
    private static final int MIN_LEVEL_SPIKES = 4;
    private static final int MIN_LEVEL_TELEPORT = 8;

    @Override
    public Optional<Trap> createRandomTrap(final Position position, final int level) {
        final int roll = GameRandom.nextInt(ROLL_MAX);
        if (roll < CHANCE_ROCK) {
            return Optional.of(createRockTrap(position));
        } else if (roll < CHANCE_SPIKES) {
            if (level >= MIN_LEVEL_SPIKES) {
                return Optional.of(createPitOfSpikesTrap(position));
            } else {
                return Optional.of(createRockTrap(position));
            }
        } else {
            if (level >= MIN_LEVEL_TELEPORT) {
                return Optional.of(createTeleportTrap(position));
            } else if (level >= MIN_LEVEL_SPIKES) {
                return Optional.of(createPitOfSpikesTrap(position));
            } else {
                return Optional.of(createRockTrap(position));
            }
        }
    }

    @Override
    public Trap createRockTrap(final Position position) {
        return new RockTrap(position);
    }

    @Override
    public Trap createPitOfSpikesTrap(final Position position) {
        return new PitOfSpikesTrap(position);
    }

    @Override
    public Trap createTeleportTrap(final Position position) {
        return new TeleportTrap(position);
    }
}
