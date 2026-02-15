package it.unibo.jrogue.controller.generation.api;

/**
 * Configuration for level generation.
 * Using a record for immutability.
 *
 * @param mapWidth the map width in tiles
 * @param mapHeight the map height in tiles
 * @param minRoomSize minimum room dimension
 * @param maxRoomSize maximum room dimension
 * @param minPartitionSize minimum BSP partition size
 * @param maxDepth maximum BSP tree recursion depth
 * @param seed random seed for reproducibility
 * @param levelNumber current level number (affects difficulty)
 */
public record GenerationConfig(
    int mapWidth,
    int mapHeight,
    int minRoomSize,
    int maxRoomSize,
    int minPartitionSize,
    int maxDepth,
    long seed,
    int levelNumber
) {

    /**
     * Default minimum room size.
     */
    public static final int DEFAULT_MIN_ROOM_SIZE = 5;

    /**
     * Default maximum room size.
     */
    public static final int DEFAULT_MAX_ROOM_SIZE = 15;

    /**
     * Default minimum partition size.
     */
    public static final int DEFAULT_MIN_PARTITION_SIZE = 10;

    /**
     * Default maximum BSP depth.
     */
    public static final int DEFAULT_MAX_DEPTH = 5;

    /**
     * Creates a GenerationConfig from screen dimensions and tile size.
     *
     * @param screenWidth screen width in pixels
     * @param screenHeight screen height in pixels
     * @param tileSize tile size in pixels
     * @param levelNumber current level number
     * @param seed random seed
     * @return a new GenerationConfig
     */
    public static GenerationConfig fromScreenSize(
            final int screenWidth,
            final int screenHeight,
            final int tileSize,
            final int levelNumber,
            final long seed) {

        // Calculate map size based on viewport plus buffer for scrolling
        final int viewportTilesX = screenWidth / tileSize;
        final int viewportTilesY = screenHeight / tileSize;
        final int buffer = 20;

        return new GenerationConfig(
            viewportTilesX + buffer,
            viewportTilesY + buffer,
            DEFAULT_MIN_ROOM_SIZE,
            DEFAULT_MAX_ROOM_SIZE,
            DEFAULT_MIN_PARTITION_SIZE,
            DEFAULT_MAX_DEPTH,
            seed,
            levelNumber
        );
    }

    /**
     * Creates a default configuration with specified dimensions.
     *
     * @param mapWidth map width in tiles
     * @param mapHeight map height in tiles
     * @param levelNumber current level number
     * @param seed random seed
     * @return a new GenerationConfig
     */
    public static GenerationConfig withDefaults(
            final int mapWidth,
            final int mapHeight,
            final int levelNumber,
            final long seed) {

        return new GenerationConfig(
            mapWidth,
            mapHeight,
            DEFAULT_MIN_ROOM_SIZE,
            DEFAULT_MAX_ROOM_SIZE,
            DEFAULT_MIN_PARTITION_SIZE,
            DEFAULT_MAX_DEPTH,
            seed,
            levelNumber
        );
    }
}
