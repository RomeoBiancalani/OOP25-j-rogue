package it.unibo.jrogue.engine;

import it.unibo.jrogue.boundary.DungeonRenderer;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.DungeonController;
import it.unibo.jrogue.controller.generation.api.GenerationConfig;
import it.unibo.jrogue.controller.generation.api.LevelGenerator;
import it.unibo.jrogue.controller.generation.impl.BSPLevelGenerator;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.entities.impl.player.PlayerImpl;
import it.unibo.jrogue.entity.entities.impl.enemies.Bat;
import it.unibo.jrogue.entity.entities.impl.enemies.Dragon;
import it.unibo.jrogue.entity.entities.impl.enemies.HobGoblin;
import it.unibo.jrogue.entity.items.api.Inventory;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.items.impl.Amulet;
import it.unibo.jrogue.entity.items.impl.Armor;
import it.unibo.jrogue.entity.items.impl.Food;
import it.unibo.jrogue.entity.items.impl.Gold;
import it.unibo.jrogue.entity.items.impl.HealthPotion;
import it.unibo.jrogue.entity.items.impl.ItemFactoryImpl;
import it.unibo.jrogue.entity.items.impl.MeleeWeapon;
import it.unibo.jrogue.entity.items.impl.Ring;
import it.unibo.jrogue.entity.items.impl.Scroll;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Level;
import it.unibo.jrogue.entity.world.api.Room;
import it.unibo.jrogue.entity.world.api.Tile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Handles saving and loading game state to/from files.
 */
public final class SaveManager {

    private static final String SAVE_FILE_NAME = "save.dat";
    private static final int MAP_WIDTH = 80;
    private static final int MAP_HEIGHT = 45;
    private static final int INVENTORY_SCAN_SIZE = 50;
    private static final int DEFAULT_RING_HEALING = 5;
    private static final String ENEMY_BAT = "bat";
    private static final String ENEMY_GOBLIN = "goblin";
    private static final String ENEMY_DRAGON = "dragon";
    private static final String ITEM_GOLD = "gold";
    private static final String ITEM_POTION = "potion";
    private static final String ITEM_FOOD = "food";
    private static final String ITEM_RING = "ring";
    private static final String ITEM_SCROLL = "scroll";
    private static final String ITEM_WEAPON = "weapon";
    private static final String ITEM_ARMOR = "armor";
    private static final int AMULET_LEVEL = 10;

    private SaveManager() {
    }

    /**
     * Returns the default save file path.
     *
     * @return the default save path
     */
    public static Path getDefaultSavePath() {
        // Saves to /home/{user}/.jrogue/save.dat
        return Path.of(System.getProperty("user.home"), ".jrogue", SAVE_FILE_NAME);
    }

    /**
     * Saves the current game state to the specified file.
     *
     * @param dungeonController the dungeon controller to save
     * @param savePath the file path to save to
     * @throws IOException if writing fails
     */
    public static void save(final DungeonController dungeonController,
                            final Path savePath) throws IOException {
        final SaveData data = extractSaveData(dungeonController);

        final Path parent = savePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(savePath))) {
            oos.writeObject(data);
        }
    }

    /**
     * Loads game state from the specified file.
     *
     * @param savePath the file path to load from
     * @return the loaded save data
     * @throws IOException if reading fails
     * @throws ClassNotFoundException if deserialization fails
     */
    public static SaveData load(final Path savePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                Files.newInputStream(savePath))) {
            return (SaveData) ois.readObject();
        }
    }

    /**
     * Checks if a save file exists at the default path.
     *
     * @return true if a save file exists
     */
    public static boolean saveExists() {
        return Files.exists(getDefaultSavePath());
    }

    /**
     * Restores a DungeonController from saved data.
     * Regenerates the terrain using the saved seed, then places saved entities.
     *
     * @param data the save data to restore from
     * @param renderer the dungeon renderer to use
     * @return the restored dungeon controller
     */
    public static DungeonController restore(final SaveData data,
                                            final DungeonRenderer renderer) {
        final DungeonController controller = new DungeonController(data.getBaseSeed(), renderer);

        // Generate terrain
        final long levelSeed = data.getBaseSeed() + data.getCurrentLevel();
        final LevelGenerator structureGen = new BSPLevelGenerator();
        final GenerationConfig config = GenerationConfig.withDefaults(
                MAP_WIDTH, MAP_HEIGHT, data.getCurrentLevel(), levelSeed
        );
        final Level level = structureGen.generate(config);
        final GameMap map = level.getMap();

        // Restore player
        final SaveData.PlayerData pd = data.getPlayerData();
        final Position playerPos = new Position(pd.getPosX(), pd.getPosY());
        final Player player = new PlayerImpl(
                pd.getMaxHp(), pd.getLevel(), pd.getArmorClass(), playerPos
        );

        // Restore current HP (constructor sets HP to maxHp)
        final int hpDiff = pd.getMaxHp() - pd.getHp();
        if (hpDiff > 0) {
            player.damage(hpDiff);
        }

        // Restore inventory
        for (final SaveData.ItemData itemData : pd.getInventoryItems()) {
            final Optional<Item> item = createItemFromData(itemData);
            item.ifPresent(i -> player.getInventory().addItem(i));
        }

        // Restore enemies
        for (final SaveData.EnemyData ed : data.getEnemies()) {
            final Position ePos = new Position(ed.getPosX(), ed.getPosY());
            final Enemy enemy = createEnemyFromData(ed.getType(), ePos);
            map.addEntity(enemy);
        }

        // Restore items on map
        for (final SaveData.ItemData id : data.getItems()) {
            final Position iPos = new Position(id.getPosX(), id.getPosY());
            final Optional<Item> item = createItemFromData(id);
            item.ifPresent(i -> map.addItem(iPos, i));
        }

        // Spawn amulet on amulet level
        if (data.getCurrentLevel() >= AMULET_LEVEL) {
            spawnAmuletOnMap(map);
        }

        controller.restoreState(data.getCurrentLevel(), player, map);
        return controller;
    }

    private static void spawnAmuletOnMap(final GameMap map) {
        final List<Room> rooms = map.getRooms();
        final List<Room> candidateRooms = new ArrayList<>();
        for (int i = 1; i < rooms.size(); i++) {
            candidateRooms.add(rooms.get(i));
        }
        Collections.shuffle(candidateRooms);
        for (final Room room : candidateRooms) {
            final List<Position> positions = getFloorPositions(map, room);
            if (!positions.isEmpty()) {
                final Position pos = positions.get(GameRandom.nextInt(positions.size()));
                Amulet amulet = (Amulet) new ItemFactoryImpl().createAmulet();
                map.addItem(pos, amulet);
                return;
            }
        }
    }

    private static List<Position> getFloorPositions(final GameMap map, final Room room) {
        final List<Position> positions = new ArrayList<>();
        final Position topLeft = room.getTopLeft();
        for (int y = topLeft.y() + 1; y < topLeft.y() + room.getHeight() - 1; y++) {
            for (int x = topLeft.x() + 1; x < topLeft.x() + room.getWidth() - 1; x++) {
                final Position pos = new Position(x, y);
                if (map.getTileAt(pos) == Tile.FLOOR) {
                    positions.add(pos);
                }
            }
        }
        return positions;
    }

    private static SaveData extractSaveData(final DungeonController dc) {
        final Player player = dc.getPlayer();
        final GameMap map = dc.getCurrentMap();

        // Extract player
        final List<SaveData.ItemData> inventoryItems = extractInventoryItems(player.getInventory());
        final SaveData.PlayerData playerData = new SaveData.PlayerData(
                player.getPosition().x(),
                player.getPosition().y(),
                player.getLifePoint(),
                player.getMaxLifePoint(),
                player.getLevel(),
                player.getArmorClass(),
                inventoryItems
        );

        // Extract enemies
        final List<SaveData.EnemyData> enemies = new ArrayList<>();
        for (final Enemy enemy : map.getEnemies()) {
            if (enemy.isAlive()) {
                enemies.add(new SaveData.EnemyData(
                        getEnemyType(enemy),
                        enemy.getPosition().x(),
                        enemy.getPosition().y()
                ));
            }
        }

        // Extract items
        final List<SaveData.ItemData> items = new ArrayList<>();
        for (final Map.Entry<Position, Item> entry : map.getItems().entrySet()) {
            final Position pos = entry.getKey();
            final SaveData.ItemData itemData = createItemData(entry.getValue(), pos.x(), pos.y());
            items.add(itemData);
        }

        return new SaveData(dc.getBaseSeed(), dc.getCurrentLevel(), playerData, enemies, items);
    }

    private static List<SaveData.ItemData> extractInventoryItems(final Inventory inventory) {
        final List<SaveData.ItemData> items = new ArrayList<>();
        for (int i = 0; i < INVENTORY_SCAN_SIZE; i++) {
            final Optional<Item> item = inventory.getItem(i);
            if (item.isPresent()) {
                // -1 -1 are coords for inventory
                items.add(createItemData(item.get(), -1, -1));
            }
        }
        return items;
    }

    private static SaveData.ItemData createItemData(final Item item,
                                                     final int posX, final int posY) {
        // Only ring + weapons + armors have names
        if (item instanceof Gold gold) {
            return new SaveData.ItemData(ITEM_GOLD, posX, posY, "", gold.getAmount());
        } else if (item instanceof HealthPotion) {
            return new SaveData.ItemData(ITEM_POTION, posX, posY, "", 0);
        } else if (item instanceof Food) {
            return new SaveData.ItemData(ITEM_FOOD, posX, posY, "", 0);
        } else if (item instanceof Ring ring) {
            return new SaveData.ItemData(ITEM_RING, posX, posY, ring.getName(), ring.getBonus());
        } else if (item instanceof Scroll) {
            return new SaveData.ItemData(ITEM_SCROLL, posX, posY, "", 0);
        } else if (item instanceof MeleeWeapon weapon) {
            return new SaveData.ItemData(ITEM_WEAPON, posX, posY, weapon.getName(), weapon.getBonus());
        } else if (item instanceof Armor armor) {
            return new SaveData.ItemData(ITEM_ARMOR, posX, posY, armor.getName(), armor.getBonus());
        }
        return new SaveData.ItemData(ITEM_GOLD, posX, posY, "", 1);
    }

    private static Optional<Item> createItemFromData(final SaveData.ItemData data) {
        // ER = error when the name of the item is required but something went wrong during save so "default" name is used
        return switch (data.getType()) {
            case ITEM_GOLD -> Optional.of(new Gold(Math.max(1, data.getValue())));
            case ITEM_POTION -> Optional.of(new HealthPotion());
            case ITEM_FOOD -> Optional.of(new Food());
            case ITEM_RING -> Optional.of(new Ring(
                    data.getName().isEmpty() ? "Anello (ER)" : data.getName(),
                    data.getValue() > 0 ? data.getValue() : DEFAULT_RING_HEALING));
            case ITEM_SCROLL -> Optional.of(new Scroll());
            case ITEM_WEAPON -> Optional.of(new MeleeWeapon(
                    data.getName().isEmpty() ? "Arma (ER)" : data.getName(),
                    data.getValue()));
            case ITEM_ARMOR -> Optional.of(new Armor(
                    data.getName().isEmpty() ? "Armatura (ER)" : data.getName(),
                    data.getValue()));
            default -> Optional.empty();
        };
    }

    private static String getEnemyType(final Enemy enemy) {
        if (enemy instanceof Bat) {
            return ENEMY_BAT;
        } else if (enemy instanceof HobGoblin) {
            return ENEMY_GOBLIN;
        } else if (enemy instanceof Dragon) {
            return ENEMY_DRAGON;
        }
        return ENEMY_BAT;
    }

    private static Enemy createEnemyFromData(final String type, final Position pos) {
        // goblin -> new HobGoblin, dragon -> new Dragon, bat -> new Bat
        return switch (type) {
            case ENEMY_GOBLIN -> new HobGoblin(pos);
            case ENEMY_DRAGON -> new Dragon(pos);
            default -> new Bat(pos);
        };
    }
}
