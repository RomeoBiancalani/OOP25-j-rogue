package it.unibo.jrogue;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.generation.api.GenerationConfig;
import it.unibo.jrogue.controller.generation.api.LevelGenerator;
import it.unibo.jrogue.controller.generation.api.SpawnConfig;
import it.unibo.jrogue.controller.generation.impl.PopulatedLevelGenerator;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Level;
import it.unibo.jrogue.entity.world.api.Tile;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaFX application that renders the generated dungeon map using tiled graphics.
 * Uses a StackPane with two Canvas layers: terrain (layer 0) and entities (layer 1).
 */
@SuppressWarnings("PMD.SystemPrintln")
public final class WorldRenderingDemo extends StackPane {

    private static final int DEFAULT_MAP_WIDTH = 80;
    private static final int DEFAULT_MAP_HEIGHT = 45;
    private static final int DEFAULT_TILE_SIZE = 24;
    private static final String TILESET_PATH = "/tileset/";
    private static final String TILE_CORRIDOR_H = "corridorhorizontal";
    private static final String TILE_STAIRS = "stairs";
    private static final String TILE_FLOOR = "tile";
    private static final String WALL_COLOR = "#1a1a2e";

    private static final List<String> TILE_NAMES = List.of(
            TILE_FLOOR, "tiletopleft", "tiletop", "tiletopright",
            "tileleft", "tileright",
            "tilebottomleft", "tilebottom", "tilebottomright",
            TILE_CORRIDOR_H, "corridorhorizontalleft", "corridorhorizontalright",
            "corridorvertical", "corridorverticaltop", "corridorverticalbottom",
            "gold", TILE_STAIRS
    );

    private final Map<String, Image> tileImages = new HashMap<>();


    /**
     * Constructor to generate the game map
     * */
    public WorldRenderingDemo(){
        final long seed = System.currentTimeMillis();
        loadTileset();
        final GameMap map = generateMap(seed);

        final int canvasWidth = map.getWidth() * DEFAULT_TILE_SIZE;
        final int canvasHeight = map.getHeight() * DEFAULT_TILE_SIZE;

        final Canvas terrainCanvas = new Canvas(canvasWidth, canvasHeight);
        final Canvas entityCanvas = new Canvas(canvasWidth, canvasHeight);

        terrainCanvas.getGraphicsContext2D().setImageSmoothing(false);
        entityCanvas.getGraphicsContext2D().setImageSmoothing(false);

        renderTerrain(terrainCanvas.getGraphicsContext2D(), map);
        renderEntities(entityCanvas.getGraphicsContext2D(), map);
        this.getChildren().addAll(terrainCanvas, entityCanvas);
    }

//Keeping this because it may be something wrong with the constructor
/*
    public void start(final Stage primaryStage) {
        final long seed = System.currentTimeMillis();
        final int screenWidth = DEFAULT_SCREEN_WIDTH;
        final int screenHeight = DEFAULT_SCREEN_HEIGHT;

        loadTileset();

        final GameMap map = generateMap(seed);

        final int canvasWidth = map.getWidth() * DEFAULT_TILE_SIZE;
        final int canvasHeight = map.getHeight() * DEFAULT_TILE_SIZE;

        final Canvas terrainCanvas = new Canvas(canvasWidth, canvasHeight);
        final Canvas entityCanvas = new Canvas(canvasWidth, canvasHeight);

        renderTerrain(terrainCanvas.getGraphicsContext2D(), map);
        renderEntities(entityCanvas.getGraphicsContext2D(), map);

        final StackPane layers = new StackPane(terrainCanvas, entityCanvas);
        final ScrollPane scroll = new ScrollPane(layers);

        final Scene scene = new Scene(scroll, screenWidth, screenHeight);
        scene.setFill(Color.BLACK);

        primaryStage.setTitle("World Rendering Demo - Seed: " + seed);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
*/

    private void loadTileset() {
        for (final String name : TILE_NAMES) {
            final String path = TILESET_PATH + name + ".png";
            final var resource = getClass().getResourceAsStream(path);
            if (resource != null) {
                tileImages.put(name, new Image(resource));
            } else {
                System.err.println("Missing image: " + path);
            }
        }
    }

    private GameMap generateMap(final long seed) {
        final SpawnConfig spawnConfig = SpawnConfig.debug();
        final LevelGenerator generator = new PopulatedLevelGenerator(spawnConfig);
        final GenerationConfig config = GenerationConfig.withDefaults(
                DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, 1, seed
        );
        final Level level = generator.generate(config);
        return level.getMap();
    }

    private void renderTerrain(final GraphicsContext gc, final GameMap map) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, map.getWidth() * DEFAULT_TILE_SIZE, map.getHeight() * DEFAULT_TILE_SIZE);

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                final Position pos = new Position(x, y);
                final Tile tile = map.getTileAt(pos);
                // Calculated positions (px and py are upper left coords)
                final double px = x * DEFAULT_TILE_SIZE;
                final double py = y * DEFAULT_TILE_SIZE;

                switch (tile) {
                    case FLOOR -> drawFloor(gc, map, pos, px, py);
                    case WALL -> drawWallFill(gc, px, py);
                    case CORRIDOR -> drawCorridor(gc, map, pos, px, py);
                    case DOOR -> drawColoredTile(gc, Color.SADDLEBROWN, px, py);
                    case STAIRS_DOWN, STAIRS_UP -> drawImage(gc, TILE_STAIRS, px, py);
                    case TRAP -> drawColoredTile(gc, Color.DARKORANGE, px, py);
                    case VOID -> { }
                }
            }
        }
    }

    private void drawFloor(final GraphicsContext gc, final GameMap map,
                           final Position pos, final double px, final double py) {
        final boolean wallAbove = isWallOrVoid(map, pos.x(), pos.y() - 1);
        final boolean wallBelow = isWallOrVoid(map, pos.x(), pos.y() + 1);
        final boolean wallLeft = isWallOrVoid(map, pos.x() - 1, pos.y());
        final boolean wallRight = isWallOrVoid(map, pos.x() + 1, pos.y());

        final String tileName;
        if (wallAbove && wallLeft) {
            tileName = "tiletopleft";
        } else if (wallAbove && wallRight) {
            tileName = "tiletopright";
        } else if (wallBelow && wallLeft) {
            tileName = "tilebottomleft";
        } else if (wallBelow && wallRight) {
            tileName = "tilebottomright";
        } else if (wallAbove) {
            tileName = "tiletop";
        } else if (wallBelow) {
            tileName = "tilebottom";
        } else if (wallLeft) {
            tileName = "tileleft";
        } else if (wallRight) {
            tileName = "tileright";
        } else {
            tileName = TILE_FLOOR;
        }

        drawImage(gc, tileName, px, py);
    }

    private void drawWallFill(final GraphicsContext gc, final double px, final double py) {
        gc.setFill(Color.web(WALL_COLOR));
        gc.fillRect(px, py, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
    }

    private void drawCorridor(final GraphicsContext gc, final GameMap map,
                              final Position pos, final double px, final double py) {
        final boolean wallAbove = isWallOrVoid(map, pos.x(), pos.y() - 1);
        final boolean wallBelow = isWallOrVoid(map, pos.x(), pos.y() + 1);
        final boolean wallLeft = isWallOrVoid(map, pos.x() - 1, pos.y());
        final boolean wallRight = isWallOrVoid(map, pos.x() + 1, pos.y());

        final String tileName;
        if (wallAbove && wallBelow) {
            if (wallLeft) {
                tileName = "corridorhorizontalleft";
            } else if (wallRight) {
                tileName = "corridorhorizontalright";
            } else {
                tileName = TILE_CORRIDOR_H;
            }
        } else if (wallLeft && wallRight) {
            if (wallAbove) {
                tileName = "corridorverticaltop";
            } else if (wallBelow) {
                tileName = "corridorverticalbottom";
            } else {
                tileName = "corridorvertical";
            }
        } else {
            tileName = TILE_FLOOR;
        }

        drawImage(gc, tileName, px, py);
    }

    private void drawColoredTile(final GraphicsContext gc, final Color color,
                                 final double px, final double py) {
        gc.setFill(color);
        gc.fillRect(px, py, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
    }

    private void drawImage(final GraphicsContext gc, final String name,
                           final double px, final double py) {
        final Image img = tileImages.get(name);
        if (img != null) {
            gc.drawImage(img, px, py, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
        }
    }

    private boolean isWallOrVoid(final GameMap map, final int x, final int y) {
        final Tile tile = map.getTileAt(new Position(x, y));
        return tile == Tile.WALL || tile == Tile.VOID;
    }

    private void renderEntities(final GraphicsContext gc, final GameMap map) {
        for (final Position pos : map.getItems().keySet()) {
            final double px = pos.x() * DEFAULT_TILE_SIZE;
            final double py = pos.y() * DEFAULT_TILE_SIZE;
            drawImage(gc, "gold", px, py);
        }

        // Enemies
        gc.setFill(Color.LIMEGREEN);
        for (final Enemy enemy : map.getEnemies()) {
            final Position pos = enemy.getPosition();
            final double px = pos.x() * DEFAULT_TILE_SIZE;
            final double py = pos.y() * DEFAULT_TILE_SIZE;
            gc.fillRect(px, py, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
        }

        // Player
        final Position start = map.getStartingPosition();
        gc.setFill(Color.RED);
        gc.fillRect(
                start.x() * DEFAULT_TILE_SIZE,
                start.y() * DEFAULT_TILE_SIZE,
                DEFAULT_TILE_SIZE,
                DEFAULT_TILE_SIZE
        );
    }
}