package it.unibo.jrogue.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jrogue.boundary.DungeonRenderer;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.generation.api.GenerationConfig;
import it.unibo.jrogue.controller.generation.api.LevelGenerator;
import it.unibo.jrogue.controller.generation.api.SpawnConfig;
import it.unibo.jrogue.controller.generation.impl.PopulatedLevelGenerator;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.entities.impl.PlayerImpl;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Level;
import it.unibo.jrogue.entity.world.api.Tile;

/**
 * Manages the dungeon game state across 10 levels.
 * Handles level generation, progression, and per-turn game logic.
 */
public final class DungeonController {

    /** Maximum number of dungeon levels. */
    public static final int MAX_LEVEL = 10;

    private static final int MAP_WIDTH = 80;
    private static final int MAP_HEIGHT = 45;
    private static final int PLAYER_START_HP = 20;
    private static final int PLAYER_START_LEVEL = 1;
    private static final int PLAYER_START_AC = 5;

    private final long baseSeed;
    private final DungeonRenderer renderer;
    private final LevelGenerator generator;

    private int currentLevel;
    private GameMap currentMap;
    private Player player;
    private MovementControllerImpl movementController;

    /**
     * Creates a DungeonController with the given seed and renderer.
     *
     * @param seed the base seed for level generation
     * @param renderer the dungeon renderer
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Renderer is shared between instances by design")
    public DungeonController(final long seed, final DungeonRenderer renderer) {
        this.baseSeed = seed;
        this.renderer = renderer;
        this.generator = new PopulatedLevelGenerator(SpawnConfig.defaults());
        this.currentLevel = 0;
    }

    /**
     * Starts a new game from level 1.
     * Creates the player and generates the first level.
     */
    public void startNewGame() {
        this.currentLevel = 1;
        generateCurrentLevel();

        final Position startPos = currentMap.getStartingPosition();
        this.player = new PlayerImpl(PLAYER_START_HP, PLAYER_START_LEVEL, PLAYER_START_AC, startPos);
        currentMap.setPlayer(player);

        this.movementController = new MovementControllerImpl(currentMap);

        renderer.initForMap(currentMap);
        renderer.renderAll(currentMap, player);
    }

    /**
     * Advances to the next dungeon level.
     * Transfers the player to the new level's starting position.
     *
     * @return true if advanced successfully, false if already at max level
     */
    public boolean nextLevel() {
        if (currentLevel >= MAX_LEVEL) {
            return false;
        }

        currentLevel++;
        generateCurrentLevel();

        player.setPosition(currentMap.getStartingPosition());
        currentMap.setPlayer(player);

        this.movementController = new MovementControllerImpl(currentMap);

        renderer.initForMap(currentMap);
        renderer.renderAll(currentMap, player);
        return true;
    }

    /**
     * Executes a single game turn: moves the player, then enemies.
     * Updates the entity and item rendering layers.
     *
     * @param move the player's movement direction
     */
    public void executeTurn(final Move move) {
        movementController.executeTurn(move);
        renderer.renderEntities(currentMap, player);
        renderer.renderItems(currentMap);
    }

    /**
     * Checks if the player is currently standing on stairs.
     *
     * @return true if the player is on stairs
     */
    public boolean isOnStairs() {
        final Tile tile = currentMap.getTileAt(player.getPosition());
        return tile == Tile.STAIRS_UP;
    }

    /**
     * Returns the current dungeon level number (1-indexed).
     *
     * @return the current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Returns the current game map.
     *
     * @return the game map
     */
    public GameMap getCurrentMap() {
        return currentMap;
    }

    /**
     * Returns the player entity.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the base seed used for level generation.
     *
     * @return the base seed
     */
    public long getBaseSeed() {
        return baseSeed;
    }

    /**
     * Returns the dungeon renderer.
     *
     * @return the renderer
     */
    public DungeonRenderer getRenderer() {
        return renderer;
    }

    /**
     * Restores the dungeon controller from saved state.
     * Used by the save/load system to recreate a game in progress.
     *
     * @param level the level to restore to
     * @param restoredPlayer the restored player
     * @param restoredMap the restored game map
     */
    public void restoreState(final int level, final Player restoredPlayer, final GameMap restoredMap) {
        this.currentLevel = level;
        this.currentMap = restoredMap;
        this.player = restoredPlayer;
        this.currentMap.setPlayer(player);
        this.movementController = new MovementControllerImpl(currentMap);

        renderer.initForMap(currentMap);
        renderer.renderAll(currentMap, player);
    }

    /**
     * Generates the current level using the custom seed.
     * Each level is reproducible: seed = baseSeed + levelNumber.
     */
    private void generateCurrentLevel() {
        final long levelSeed = baseSeed + currentLevel;
        final GenerationConfig config = GenerationConfig.withDefaults(
                MAP_WIDTH, MAP_HEIGHT, currentLevel, levelSeed
        );
        final Level level = generator.generate(config);
        this.currentMap = level.getMap();
    }

}
