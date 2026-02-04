package it.unibo.rogue.controller.generationController.impl;

import it.unibo.rogue.commons.Dice;
import it.unibo.rogue.controller.generationController.api.EntityPopulator;
import it.unibo.rogue.controller.generationController.api.GenerationConfig;
import it.unibo.rogue.controller.generationController.api.LevelGenerator;
import it.unibo.rogue.controller.generationController.api.SpawnConfig;
import it.unibo.rogue.entity.world.api.Level;

/**
 * Level generator that combines structure generation with entity population.
 */
public final class PopulatedLevelGenerator implements LevelGenerator {

    private final LevelGenerator structureGenerator;
    private final EntityPopulator entityPopulator;
    private final SpawnConfig spawnConfig;

    /**
     * Creates a PopulatedLevelGenerator with default spawn configuration.
     */
    public PopulatedLevelGenerator() {
        this(SpawnConfig.defaults());
    }

    /**
     * Creates a PopulatedLevelGenerator with custom spawn configuration.
     *
     * @param spawnConfig the spawn configuration to use
     */
    public PopulatedLevelGenerator(final SpawnConfig spawnConfig) {
        this.structureGenerator = new BSPLevelGenerator();
        this.entityPopulator = new EntityPopulatorImpl();
        this.spawnConfig = spawnConfig;
    }

    /**
     * Creates a PopulatedLevelGenerator with custom components.
     *
     * @param structureGenerator the generator for dungeon structure
     * @param entityPopulator the populator for entities
     * @param spawnConfig the spawn configuration
     */
    public PopulatedLevelGenerator(final LevelGenerator structureGenerator,
                                   final EntityPopulator entityPopulator,
                                   final SpawnConfig spawnConfig) {
        this.structureGenerator = structureGenerator;
        this.entityPopulator = entityPopulator;
        this.spawnConfig = spawnConfig;
    }

    /**
     * Generates a fully populated dungeon level.
     *
     * @param config the generation configuration
     * @return the generated and populated level
     */
    @Override
    public Level generate(final GenerationConfig config) {
        // Seed all random sources for deterministic generation
        Dice.setSeed(config.seed());

        // Generate dungeon structure
        final Level level = structureGenerator.generate(config);

        // Populate with entities
        entityPopulator.populate(level.getMap(), config.levelNumber(), spawnConfig);

        return level;
    }

    @Override
    public void setSeed(final long seed) {
        // TODO: Understand the best solution for deterministic randomness
        structureGenerator.setSeed(seed);
    }

    /**
     * Returns the spawn configuration being used.
     *
     * @return the spawn configuration
     */
    public SpawnConfig getSpawnConfig() {
        return spawnConfig;
    }
}
