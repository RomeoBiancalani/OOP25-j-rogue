package it.unibo.jrogue.controller.generation.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.generation.api.EntityPopulator;
import it.unibo.jrogue.controller.generation.api.SpawnConfig;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.impl.enemies.Bat;
import it.unibo.jrogue.entity.entities.impl.enemies.Dragon;
import it.unibo.jrogue.entity.entities.impl.enemies.HobGoblin;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.items.api.ItemFactory;
import it.unibo.jrogue.entity.items.impl.ItemFactoryImpl;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Room;
import it.unibo.jrogue.entity.world.api.Tile;
import it.unibo.jrogue.entity.world.impl.SimpleTrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of EntityPopulator that populates rooms with
 * items, enemies, and traps based on configuration.
 */
public final class EntityPopulatorImpl implements EntityPopulator {

    private static final int SPIKE_TRAP_DAMAGE = 5;
    private static final int POISON_TRAP_DAMAGE = 2;
    private static final int TELEPORT_TRAP_DAMAGE = 0;

    private final ItemFactory itemFactory;

    /**
     * Creates a new EntityPopulator.
     */
    public EntityPopulatorImpl() {
        this.itemFactory = new ItemFactoryImpl();
    }

    @Override
    public void populate(final GameMap map, final int levelNumber, final SpawnConfig config) {
        final List<Room> rooms = map.getRooms();

        // Skip first room (player spawn)
        for (int i = 1; i < rooms.size(); i++) {
            populateRoom(map, rooms.get(i), levelNumber, config);
        }

        if (levelNumber == 1) {
            spawnAmulet(map, rooms);
        }
    }

    private void spawnAmulet(final GameMap map, final List<Room> rooms) {
        if (rooms.size() > 1) {
            final Room randomRoom = rooms.get(1 + GameRandom.nextInt(rooms.size() - 1));
            final List<Position> positions = getFloorPositions(map, randomRoom);

            if (!positions.isEmpty()) {
                final Position pos = positions.get(GameRandom.nextInt(positions.size()));
                map.addItem(pos, itemFactory.createAmulet());
            }
        }
    }

    @Override
    public void setSeed(final long seed) {
        GameRandom.setSeed(seed);
    }

    /**
     * Populates a single room with items, enemies, and traps.
     *
     * @param map         the game map
     * @param room        the room to populate
     * @param levelNumber the dungeon level
     * @param config      spawn configuration
     */
    private void populateRoom(final GameMap map, final Room room,
            final int levelNumber, final SpawnConfig config) {
        final List<Position> availablePositions = getFloorPositions(map, room);
        if (availablePositions.isEmpty()) {
            return;
        }

        spawnLoot(map, room, availablePositions, levelNumber);
        spawnTraps(map, room, availablePositions, levelNumber, config);
        spawnEnemies(map, availablePositions, levelNumber, config);
    }

    /**
     * Gets all floor positions within a room.
     *
     * @param map  the game map
     * @param room the room to get floor positions from
     * @return list of floor positions
     */
    private List<Position> getFloorPositions(final GameMap map, final Room room) {
        final List<Position> positions = new ArrayList<>();
        final Position topLeft = room.getTopLeft();

        // Positions of the room (without walls)
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

    private void spawnLoot(final GameMap map, final Room room, final List<Position> positions, final int level) {
        final int itemsToSpawn = GameRandom.nextInt(4);

        for (int i = 0; i < itemsToSpawn && !positions.isEmpty(); i++) {

            Position pos = pickRandomPosition(positions);

            final Optional<Item> item = itemFactory.createRandomItem(level);
            item.ifPresent(it -> {
                map.addItem(pos, it);
                addItemToRoom(room, it);
            });
        }

    }

    /**
     * Spawns traps based on level requirements.
     *
     * @param map         the game map
     * @param room        the room to spawn traps in
     * @param positions   available positions for spawning
     * @param levelNumber the dungeon level
     * @param config      spawn configuration
     */
    private void spawnTraps(final GameMap map, final Room room,
            final List<Position> positions,
            final int levelNumber, final SpawnConfig config) {
        if (!rollChance(config.trapRate()) || positions.isEmpty()) {
            return;
        }

        // Collect available trap types based on level
        final List<TrapType> availableTraps = new ArrayList<>();
        if (levelNumber >= config.spikeTrapMinLevel()) {
            availableTraps.add(TrapType.SPIKE);
        }
        if (levelNumber >= config.poisonTrapMinLevel()) {
            availableTraps.add(TrapType.POISON);
        }
        if (levelNumber >= config.teleportTrapMinLevel()) {
            availableTraps.add(TrapType.TELEPORT);
        }

        if (availableTraps.isEmpty()) {
            return;
        }

        // Pick random trap type and spawn
        final TrapType type = availableTraps.get(GameRandom.nextInt(availableTraps.size()));
        final Position pos = pickRandomPosition(positions);
        final int damage = switch (type) {
            case SPIKE -> SPIKE_TRAP_DAMAGE;
            // TODO: Let's understand if we want to implement poison that lasts for multiple
            // steps
            case POISON -> POISON_TRAP_DAMAGE;
            case TELEPORT -> TELEPORT_TRAP_DAMAGE;
        };

        final SimpleTrap trap = new SimpleTrap(pos, damage);
        addTrapToRoom(room, trap);

        map.setTileAt(pos, Tile.TRAP);
    }

    /**
     * Spawns enemies using weighted selection based on level.
     *
     * @param map         the game map
     * @param positions   available positions for spawning
     * @param levelNumber the dungeon level
     * @param config      spawn configuration
     */
    private void spawnEnemies(final GameMap map, final List<Position> positions,
            final int levelNumber, final SpawnConfig config) {
        int enemyCount = 0;

        while (enemyCount < config.maxEnemiesPerRoom()
                && rollChance(config.enemySpawnRate())
                && !positions.isEmpty()) {

            final Position pos = pickRandomPosition(positions);
            final Enemy enemy = createWeightedEnemy(pos, levelNumber, config);

            map.addEntity(enemy);

            positions.remove(pos);
            enemyCount++;
        }
    }

    /**
     * Creates an enemy using weighted random selection.
     * Stronger enemies become more likely at deeper levels.
     *
     * @param pos    the position for the enemy
     * @param level  the dungeon level
     * @param config spawn configuration
     * @return the created enemy
     */
    private Enemy createWeightedEnemy(final Position pos, final int level, final SpawnConfig config) {
        // Calculate weights for each enemy type
        final int batWeight = config.getEnemyWeight(0, level);
        final int goblinWeight = config.getEnemyWeight(1, level);
        final int dragonWeight = config.getEnemyWeight(2, level);

        final int totalWeight = batWeight + goblinWeight + dragonWeight;
        int roll = GameRandom.nextInt(totalWeight);

        roll -= batWeight;
        if (roll < 0) {
            return new Bat(pos);
        }

        roll -= goblinWeight;
        if (roll < 0) {
            return new HobGoblin(pos);
        }

        return new Dragon(pos);
    }

    /**
     * Rolls a probability check.
     *
     * @param probability the probability (0.0 to 1.0)
     * @return true if the roll succeeds
     */
    private boolean rollChance(final double probability) {
        return GameRandom.nextDouble() < probability;
    }

    /**
     * Picks and removes a random position from the list.
     *
     * @param positions the list of available positions
     * @return the selected position
     */
    private Position pickRandomPosition(final List<Position> positions) {
        final int index = GameRandom.nextInt(positions.size());
        return positions.remove(index);
    }

    /**
     * Adds an item to a room (for room-level tracking).
     *
     * @param room the room to add item to
     * @param item the item to add
     */
    private void addItemToRoom(final Room room, final Item item) {
        room.addItem(item);
    }

    /**
     * Adds a trap to a room.
     *
     * @param room the room to add trap to
     * @param trap the trap to add
     */
    private void addTrapToRoom(final Room room, final SimpleTrap trap) {
        room.addTrap(trap);
    }

    /**
     * Trap types available for spawning.
     */
    private enum TrapType {
        SPIKE,
        POISON,
        TELEPORT
    }
}
