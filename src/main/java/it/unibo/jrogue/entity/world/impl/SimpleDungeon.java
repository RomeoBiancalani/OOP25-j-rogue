package it.unibo.jrogue.entity.world.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.jrogue.entity.world.api.Dungeon;
import it.unibo.jrogue.entity.world.api.Level;

/**
 * Simple implementation of a dungeon.
 */
public final class SimpleDungeon implements Dungeon {

    private final List<Level> levels;
    private int currentLevelIndex;
    private int points;

    /**
     * Creates a new dungeon with the given levels.
     *
     * @param levels the levels in this dungeon
     */
    public SimpleDungeon(final List<Level> levels) {
        if (levels.isEmpty()) {
            throw new IllegalArgumentException("Dungeon must have at least one level");
        }
        this.levels = new ArrayList<>(levels);
        this.currentLevelIndex = 0;
        this.points = 0;
    }

    @Override
    public Optional<Level> enterNextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
            return Optional.of(levels.get(currentLevelIndex));
        }
        return Optional.empty();
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    @Override
    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    @Override
    public int getTotalLevels() {
        return levels.size();
    }

    /**
     * Adds points to the score.
     *
     * @param amount the amount to add
     */
    public void addPoints(final int amount) {
        points += amount;
    }

    /**
     * Adds a new level to the dungeon.
     *
     * @param level the level to add
     */
    public void addLevel(final Level level) {
        levels.add(level);
    }
}
