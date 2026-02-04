package it.unibo.rogue.controller.generationController.api;

import it.unibo.rogue.entity.world.api.GameMap;

/**
 * Populates a map with items, enemies, and traps.
 * Called after the level structure is generated.
 */
public interface EntityPopulator {

    /**
     * Populates the entire map with items, enemies, and traps.
     * Skips the first room (player spawn room).
     *
     * @param map the game map to populate
     * @param levelNumber current dungeon level (affects spawns)
     * @param config spawn configuration
     */
    void populate(GameMap map, int levelNumber, SpawnConfig config);

    /**
     * Sets the random seed for reproducible spawning.
     * (This can be used for debug but by default this.random will be Dice.getRandom())
     *
     * @param seed the seed value
     */
    void setSeed(long seed);
}
