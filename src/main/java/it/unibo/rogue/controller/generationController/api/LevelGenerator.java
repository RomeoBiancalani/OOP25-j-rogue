package it.unibo.rogue.controller.generationController.api;

import it.unibo.rogue.entity.world.api.Level;

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
     * (This can be used for debug but by default this.random will be Dice.getRandom())
     *
     * @param seed the seed value
     */
    void setSeed(long seed);
}
