package it.unibo.jrogue.entity.world.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Level;

/**
 * Simple implementation of a dungeon level.
 */
public final class SimpleLevel implements Level {

    private final GameMap map;
    private final int levelNumber;
    private final int difficulty;

    /**
     * Creates a new level.
     *
     * @param map the game map for this level
     * @param levelNumber the level number (1-indexed)
     */
    public SimpleLevel(final GameMap map, final int levelNumber) {
        this(map, levelNumber, levelNumber);
    }

    /**
     * Creates a new level with custom difficulty.
     *
     * @param map the game map for this level
     * @param levelNumber the level number (1-indexed)
     * @param difficulty the difficulty modifier
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
                        justification = "GameMap is intentionally mutable for gameplay")
    public SimpleLevel(final GameMap map, final int levelNumber, final int difficulty) {
        this.map = map;
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
    }

    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
                        justification = "GameMap reference sharing is intentional")
    public GameMap getMap() {
        return map;
    }

    @Override
    public int getLevel() {
        return levelNumber;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }
}
