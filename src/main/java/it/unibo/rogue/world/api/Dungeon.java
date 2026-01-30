package it.unibo.rogue.world.api;

import java.util.Optional;

/**
 * Represents the entire dungeon containing multiple levels.
 */
public interface Dungeon {

    /**
     * Moves to the next level of the dungeon.
     * @return the new level, or empty if already at the bottom
     */
    Optional<Level> enterNextLevel();

    /**
     * Returns the current accumulated points/score.
     * @return the points
     */
    int getPoints();

    /**
     * Returns the current level.
     * @return the current level
     */
    Level getCurrentLevel();

    /**
     * Returns the current level number.
     * @return the level number
     */
    int getCurrentLevelNumber();

    /**
     * Returns the total number of levels in this dungeon.
     * @return the total levels
     */
    int getTotalLevels();
}
