package it.unibo.rogue.entity.world.api;

/**
 * Represents a single level/floor of the dungeon.
 */
public interface Level {

    /**
     * Returns the map of this level.
     * @return the game map
     */
    GameMap getMap();

    /**
     * Returns the level number (1-indexed).
     * @return the level number
     */
    int getLevel();

    /**
     * Returns the difficulty modifier for this level.
     * Higher levels typically have increased difficulty.
     * @return the difficulty modifier
     */
    int getDifficulty();
}
