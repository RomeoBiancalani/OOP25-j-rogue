package it.unibo.rogue.generation.api;

import it.unibo.rogue.world.api.Level;

/**
 * Interface for level generation algorithms.
 */
public interface LevelGenerator {

    /**
     * Generates a new dungeon level based on the provided configuration.
     *
     * @param config the generation configuration
     * @return the generated level
     */
    Level generate(GenerationConfig config);

    /**
     * Sets the random seed for reproducible generation.
     *
     * @param seed the seed value
     */
    void setSeed(long seed);
}
