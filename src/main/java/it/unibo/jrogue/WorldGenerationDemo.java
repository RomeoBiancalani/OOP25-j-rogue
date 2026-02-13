package it.unibo.jrogue;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.generation.api.GenerationConfig;
import it.unibo.jrogue.controller.generation.api.LevelGenerator;
import it.unibo.jrogue.controller.generation.api.SpawnConfig;
import it.unibo.jrogue.controller.generation.impl.PopulatedLevelGenerator;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Level;
import it.unibo.jrogue.entity.world.api.Tile;

import java.util.Map;

/**
 * Demo application for testing world generation.
 * Prints the generated dungeon map to the console.
 * ./gradlew run -Pmain=it.unibo.jrogue.WorldGenerationDemo --args="12349876"
 * Usage: java WorldGenerationDemo [seed] [screenWidth] [screenHeight] [tileSize]
 * Parameters are optional and will use defaults if not provided.
 * Screen dimensions are stored for future JavaFX integration.
 */
@SuppressWarnings("PMD.SystemPrintln")
public final class WorldGenerationDemo {

    private static final int DEFAULT_MAP_WIDTH = 80;
    private static final int DEFAULT_MAP_HEIGHT = 40;
    private static final int DEFAULT_SCREEN_WIDTH = 1280;
    private static final int DEFAULT_SCREEN_HEIGHT = 720;
    private static final int DEFAULT_TILE_SIZE = 16;

    private final int screenWidth;
    private final int screenHeight;
    private final int tileSize;
    private final long seed;

    /**
     * Creates a new demo with default settings.
     */
    public WorldGenerationDemo() {
        this(System.currentTimeMillis(), DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT, DEFAULT_TILE_SIZE);
    }

    /**
     * Creates a new demo with specified settings.
     *
     * @param seed the random seed for generation
     * @param screenWidth the screen width in pixels (for future JavaFX use)
     * @param screenHeight the screen height in pixels (for future JavaFX use)
     * @param tileSize the tile size in pixels (for future JavaFX use)
     */
    public WorldGenerationDemo(final long seed, final int screenWidth, final int screenHeight, final int tileSize) {
        this.seed = seed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.tileSize = tileSize;
    }

    /**
     * Generates and prints a dungeon map to the console.
     */
    public void run() {
        System.out.println("=== World Generation Demo ===");
        System.out.println("Seed: " + seed);
        System.out.println("Screen: " + screenWidth + "x" + screenHeight + " (tile size: " + tileSize + ")");
        System.out.println();

        // Use debug spawn config for testing (higher spawn rates)
        final SpawnConfig spawnConfig = SpawnConfig.debug();
        final LevelGenerator generator = new PopulatedLevelGenerator(spawnConfig);

        final GenerationConfig config = GenerationConfig.withDefaults(
            DEFAULT_MAP_WIDTH,
            DEFAULT_MAP_HEIGHT,
            1,
            seed
        );

        final Level level = generator.generate(config);
        final GameMap map = level.getMap();

        System.out.println("Map size: " + map.getWidth() + "x" + map.getHeight());
        System.out.println("Rooms: " + map.getRooms().size());
        System.out.println("Hallways: " + map.getHallways().size());
        System.out.println("Starting position: " + map.getStartingPosition());
        map.getStairsUp().ifPresent(pos -> System.out.println("Stairs up: " + pos));
        System.out.println();

        // Show entity/item statistics
        System.out.println("=== Spawned Entities ===");
        System.out.println("Enemies: " + map.getEnemies().size());
        System.out.println("Items: " + map.getItems().size());
        System.out.println();

        // List items
        if (!map.getItems().isEmpty()) {
            System.out.println("Items placed:");
            for (final Map.Entry<Position, Item> entry : map.getItems().entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue().getDescription());
            }
            System.out.println();
        }

        printMap(map);
    }

    private void printMap(final GameMap map) {
        final Position start = map.getStartingPosition();
        final Position stairs = map.getStairsUp().orElse(null);

        for (int y = 0; y < map.getHeight(); y++) {
            final StringBuilder line = new StringBuilder();
            for (int x = 0; x < map.getWidth(); x++) {
                final Position pos = new Position(x, y);

                if (pos.equals(start)) {
                    line.append('@');  // Player starting position
                } else if (pos.equals(stairs)) {
                    line.append('<');  // Stairs up
                } else if (hasEnemyAt(map, pos)) {
                    line.append('E');  // Enemy
                } else if (map.getItems().containsKey(pos)) {
                    line.append('i');  // Item
                } else {
                    final Tile tile = map.getTileAt(pos);
                    line.append(getTileChar(tile));
                }
            }
            System.out.println(line);
        }
    }

    private boolean hasEnemyAt(final GameMap map, final Position pos) {
        return map.getEnemies().stream()
            .anyMatch(e -> e.getPosition().equals(pos));
    }

    private char getTileChar(final Tile tile) {
        return switch (tile) {
            case WALL -> '#';
            case FLOOR -> '.';
            case CORRIDOR -> ',';
            case DOOR -> '+';
            case STAIRS_UP -> '<';
            case TRAP -> '^';
            case VOID -> ' ';
        };
    }

    /**
     * Returns the configured screen width.
     * 
     * @return screen width in pixels
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the configured screen height.
     * 
     * @return screen height in pixels
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Returns the configured tile size.
     * 
     * @return tile size in pixels
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Returns the seed used for generation.
     * 
     * @return the seed
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Main entry point.
     *
     * @param args optional: [seed] [screenWidth] [screenHeight] [tileSize]
     */
    public static void main(final String[] args) {
        final long seed = args.length > 0 ? Long.parseLong(args[0]) : System.currentTimeMillis();
        final int screenWidth = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_SCREEN_WIDTH;
        final int screenHeight = args.length > 2 ? Integer.parseInt(args[2]) : DEFAULT_SCREEN_HEIGHT;
        final int tileSize = args.length > 3 ? Integer.parseInt(args[3]) : DEFAULT_TILE_SIZE;

        final WorldGenerationDemo demo = new WorldGenerationDemo(seed, screenWidth, screenHeight, tileSize);
        demo.run();
    }
}
