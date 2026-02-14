package it.unibo.jrogue.boundary;

import it.unibo.jrogue.boundary.api.GameViewRenderer;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.entities.impl.enemies.Bat;
import it.unibo.jrogue.entity.entities.impl.enemies.Dragon;
import it.unibo.jrogue.entity.entities.impl.enemies.HobGoblin;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.items.impl.Amulet;
import it.unibo.jrogue.entity.items.impl.Armor;
import it.unibo.jrogue.entity.items.impl.Food;
import it.unibo.jrogue.entity.items.impl.Gold;
import it.unibo.jrogue.entity.items.impl.HealthPotion;
import it.unibo.jrogue.entity.items.impl.MeleeWeapon;
import it.unibo.jrogue.entity.items.impl.Ring;
import it.unibo.jrogue.entity.items.impl.Scroll;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Tile;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders the dungeon using sprite graphics.
 * Uses a StackPane with three Canvas layers for optimized rendering:
 * terrain (redrawn on level change), items (redrawn on pickup),
 * and entities (redrawn every turn).
 */
public final class DungeonRenderer extends StackPane implements GameViewRenderer {

    /** Default tile size in pixels. */
    public static final int DEFAULT_TILE_SIZE = 24;

    private static final String TILESET_PATH = "/tileset/";
    private static final String TILE_CORRIDOR_H = "corridorhorizontal";
    private static final String TILE_STAIRS = "stairs";
    private static final String TILE_FLOOR = "tile";
    private static final String WALL_COLOR = "#1a1a2e";

    private static final String SPRITE_PLAYER = "entities/player";
    private static final String SPRITE_PLAYER_ARMOR = "entities/player-armored";
    private static final String SPRITE_BAT = "entities/bat";
    private static final String SPRITE_GOBLIN = "entities/goblin";
    private static final String SPRITE_DRAGON = "entities/dragon";
    private static final String SPRITE_GOLD = "items/gold";
    private static final String SPRITE_POTION = "items/potion";
    private static final String SPRITE_FOOD = "items/food";
    private static final String SPRITE_RING = "items/ring";
    private static final String SPRITE_AMULET = "items/amulet";
    private static final String SPRITE_SCROLL = "items/scroll";
    private static final String SPRITE_ARMOR_BASE = "items/armor-base";
    private static final String SPRITE_ARMOR_MAX = "items/armor-max";

    private static final String SPRITE_DAGGER = "weapons/dagger";
    private static final String SPRITE_SWORD = "weapons/sword";
    private static final String SPRITE_SHOVEL = "weapons/shovel";
    private static final String SPRITE_TRAP_DAMAGE = "traps/trap-damage";
    private static final String SPRITE_TRAP_TELEPORT = "traps/trap-teleport";

    private static final String ARMOR_HEAVY_NAME = "Armatura di ferro";

    private final int tileSize;
    private final Map<String, Image> spriteCache = new HashMap<>();

    private Canvas terrainCanvas;
    private Canvas itemCanvas;
    private Canvas entityCanvas;
    private final StatusBarGUI statusBar = new StatusBarGUI();
    private final MessageDialog messageDialog = new MessageDialog();

    private int mapWidth;
    private int mapHeight;

    /**
     * Creates a DungeonRenderer with the default tile size.
     */
    public DungeonRenderer() {
        this(DEFAULT_TILE_SIZE);
    }

    /**
     * Creates a DungeonRenderer with the specified tile size.
     * 
     * @param tileSize the size of each tile in pixels
     */
    public DungeonRenderer(final int tileSize) {
        this.tileSize = tileSize;
        loadSprites();
    }

    /**
     * Returns the current tile size.
     * 
     * @return tile size in pixels
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Initializes the canvas layers for the given map dimensions. Before any
     * render.
     * 
     * @param map the game map
     */
    @Override
    public void initForMap(final GameMap map) {
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();

        final int canvasW = mapWidth * tileSize;
        final int canvasH = mapHeight * tileSize;

        terrainCanvas = new Canvas(canvasW, canvasH);
        itemCanvas = new Canvas(canvasW, canvasH);
        entityCanvas = new Canvas(canvasW, canvasH);

        terrainCanvas.getGraphicsContext2D().setImageSmoothing(false);
        itemCanvas.getGraphicsContext2D().setImageSmoothing(false);
        entityCanvas.getGraphicsContext2D().setImageSmoothing(false);

        this.getChildren().clear();
        this.getChildren().addAll(terrainCanvas, statusBar, messageDialog, itemCanvas, entityCanvas);
        StackPane.setAlignment(statusBar, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(messageDialog, Pos.TOP_CENTER);
    }

    /**
     * Returns the spriteCache Map, this can be used by other Boundary classes that
     * needs Image instances to render elements.
     * 
     * @return The sprite map.
     */
    public Map<String, Image> getLoadedSprites() {
        return Collections.unmodifiableMap(this.spriteCache);
    }

    /**
     * Renders the complete terrain layer.
     * Call when the level changes.
     * 
     * @param map the game map
     */
    private void renderTerrain(final GameMap map) {
        final GraphicsContext gc = terrainCanvas.getGraphicsContext2D();
        // TODO: Let's check if the default color needs to be changed
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, mapWidth * tileSize, mapHeight * tileSize);

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                final Position pos = new Position(x, y);
                final Tile tile = map.getTileAt(pos);
                final double px = x * tileSize;
                final double py = y * tileSize;

                switch (tile) {
                    case FLOOR -> drawFloor(gc, map, pos, px, py);
                    case WALL -> drawWallFill(gc, px, py);
                    case CORRIDOR -> drawCorridor(gc, map, pos, px, py);
                    case STAIRS_UP -> drawSprite(gc, TILE_STAIRS, px, py);
                    // TODO: Trap is hidden by default, need to handle show and hide
                    case TRAP -> drawTrapSprite(gc, px, py);
                    case VOID -> {
                    }
                    default -> {
                    }
                }
            }
        }
    }

    /**
     * Renders the item layer. Clears previous items and redraws.
     * Call when items change (pickup, drop).
     * 
     * @param map the game map
     */
    private void renderItems(final GameMap map) {
        final GraphicsContext gc = itemCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapWidth * tileSize, mapHeight * tileSize);

        for (final Map.Entry<Position, Item> entry : map.getItems().entrySet()) {
            final Position pos = entry.getKey();
            final Item item = entry.getValue();
            final double px = pos.x() * tileSize;
            final double py = pos.y() * tileSize;

            drawSprite(gc, getItemSprite(item), px, py);
        }
    }

    /**
     * Renders the entity layer (player and enemies).
     * Call every turn after movement.
     *
     * @param map the game map
     * @param player the player entity
     */
    private void renderEntities(final GameMap map, final Player player) {
        final GraphicsContext gc = entityCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapWidth * tileSize, mapHeight * tileSize);

        for (final Enemy enemy : map.getEnemies()) {
            if (enemy.isAlive()) {
                final Position pos = enemy.getPosition();
                final double px = pos.x() * tileSize;
                final double py = pos.y() * tileSize;
                drawSprite(gc, getEnemySprite(enemy), px, py);
            }
        }

        final Position playerPos = player.getPosition();
        final double ppx = playerPos.x() * tileSize;
        final double ppy = playerPos.y() * tileSize;
        String spriteName = SPRITE_PLAYER;
        if (player.hasArmor()) {
            spriteName = SPRITE_PLAYER_ARMOR;
        }
        drawSprite(gc, spriteName, ppx, ppy);
    }

    /**
     * Renders the status bar (player stats).
     * Call every turn after movement.
     *
     * @param player the player entity
     */
    @Override
    public void updateStatus(final Player player) {
        this.statusBar.update(player);
    }

    /**
     * Renders all layers at once.
     * Convenience method for level changes.
     *
     * @param map    the game map
     * @param player the player entity
     */
    @Override
    public void renderAll(final GameMap map, final Player player) {
        renderTerrain(map);
        renderItems(map);
        renderEntities(map, player);
        updateStatus(player);
    }

    /**
     * Display the specified message.
     * 
     * @param message The message to display.
     */
    @Override
    public void displayMessage(final String message) {
        this.messageDialog.setMessage(message);
    }

    private void loadSprites() {
        // Terrain tiles
        loadSprite(TILE_FLOOR);
        loadSprite("tiletopleft");
        loadSprite("tiletop");
        loadSprite("tiletopright");
        loadSprite("tileleft");
        loadSprite("tileright");
        loadSprite("tilebottomleft");
        loadSprite("tilebottom");
        loadSprite("tilebottomright");
        loadSprite(TILE_CORRIDOR_H);
        loadSprite("corridorhorizontalleft");
        loadSprite("corridorhorizontalright");
        loadSprite("corridorvertical");
        loadSprite("corridorverticaltop");
        loadSprite("corridorverticalbottom");
        loadSprite(TILE_STAIRS);

        // Entities
        loadSprite(SPRITE_PLAYER);
        loadSprite(SPRITE_PLAYER_ARMOR);
        loadSprite(SPRITE_BAT);
        loadSprite(SPRITE_GOBLIN);
        loadSprite(SPRITE_DRAGON);

        // Items
        loadSprite(SPRITE_GOLD);
        loadSprite(SPRITE_POTION);
        loadSprite(SPRITE_FOOD);
        loadSprite(SPRITE_RING);
        loadSprite(SPRITE_AMULET);
        loadSprite(SPRITE_SCROLL);
        loadSprite(SPRITE_ARMOR_BASE);
        loadSprite(SPRITE_ARMOR_MAX);
        loadSprite(SPRITE_DAGGER);
        loadSprite(SPRITE_SWORD);
        loadSprite(SPRITE_SHOVEL);

        // Traps
        loadSprite(SPRITE_TRAP_DAMAGE);
        loadSprite(SPRITE_TRAP_TELEPORT);
    }

    private void loadSprite(final String name) {
        final String path = TILESET_PATH + name + ".png";
        final var resource = getClass().getResourceAsStream(path);
        if (resource != null) {
            // Sprite Cache contains all the sprite already loaded and ready to be rendered
            spriteCache.put(name, new Image(resource));
        }
    }

    private void drawSprite(final GraphicsContext gc, final String name,
                            final double px, final double py) {
        final Image img = spriteCache.get(name);
        if (img != null) {
            gc.drawImage(img, px, py, tileSize, tileSize);
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

        drawSprite(gc, tileName, px, py);
    }

    private void drawWallFill(final GraphicsContext gc,
                              final double px, final double py) {
        gc.setFill(Color.web(WALL_COLOR));
        gc.fillRect(px, py, tileSize, tileSize);
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

        drawSprite(gc, tileName, px, py);
    }

    private void drawTrapSprite(final GraphicsContext gc,
                                final double px, final double py) {
        // TODO: Check if trap is hidden
        drawSprite(gc, SPRITE_TRAP_DAMAGE, px, py);
    }

    private boolean isWallOrVoid(final GameMap map, final int x, final int y) {
        final Tile tile = map.getTileAt(new Position(x, y));
        return tile == Tile.WALL || tile == Tile.VOID;
    }

    public static String getItemSprite(final Item item) {
        if (item instanceof Gold) {
            return SPRITE_GOLD;
        } else if (item instanceof HealthPotion) {
            return SPRITE_POTION;
        } else if (item instanceof Food) {
            return SPRITE_FOOD;
        } else if (item instanceof Ring) {
            return SPRITE_RING;
        } else if (item instanceof Amulet) {
            return SPRITE_AMULET;
        } else if (item instanceof Scroll) {
            return SPRITE_SCROLL;
        } else if (item instanceof MeleeWeapon weapon) {
            return getWeaponSprite(weapon);
        } else if (item instanceof Armor armor) {
            return getArmorSprite(armor);
        }
        return SPRITE_GOLD;
    }

    private static String getWeaponSprite(final MeleeWeapon weapon) {
        final String weaponName = weapon.getName();
        if (weaponName.contains("Pugnale")) {
            return SPRITE_DAGGER;
        } else if (weaponName.contains("Spada")) {
            return SPRITE_SWORD;
        }
        return SPRITE_SHOVEL;
    }

    private static String getArmorSprite(final Armor armor) {
        if (ARMOR_HEAVY_NAME.equals(armor.getName())) {
            return SPRITE_ARMOR_MAX;
        }
        return SPRITE_ARMOR_BASE;
    }

    private String getEnemySprite(final Enemy enemy) {
        if (enemy instanceof Bat) {
            return SPRITE_BAT;
        } else if (enemy instanceof HobGoblin) {
            return SPRITE_GOBLIN;
        } else if (enemy instanceof Dragon) {
            return SPRITE_DRAGON;
        }
        return SPRITE_BAT;
    }

}
