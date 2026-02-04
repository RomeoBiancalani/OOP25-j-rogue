package it.unibo.rogue.controller.generationController.impl;

import it.unibo.rogue.commons.Dice;
import it.unibo.rogue.commons.Position;
import it.unibo.rogue.controller.generationController.api.EntityPopulator;
import it.unibo.rogue.controller.generationController.api.SpawnConfig;
import it.unibo.rogue.entity.gameEntities.api.Enemy;
import it.unibo.rogue.entity.gameEntities.impl.enemies.Bat;
import it.unibo.rogue.entity.gameEntities.impl.enemies.HobGoblin;
import it.unibo.rogue.entity.items.api.Item;
import it.unibo.rogue.entity.items.impl.HealthPotion;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;
import it.unibo.rogue.entity.world.api.GameMap;
import it.unibo.rogue.entity.world.api.Room;
import it.unibo.rogue.entity.world.api.Tile;
import it.unibo.rogue.entity.world.impl.SimpleGameMap;
import it.unibo.rogue.entity.world.impl.SimpleRoom;
import it.unibo.rogue.entity.world.impl.SimpleTrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of EntityPopulator that populates rooms with
 * items, enemies, and traps based on configuration.
 */
public final class EntityPopulatorImpl implements EntityPopulator {
    // TODO: This will be moved to a TrapConfig class once they are implemented
    /**
     * Damage dealt by spike traps.
     */
    private static final int SPIKE_TRAP_DAMAGE = 5;

    /**
     * Damage dealt by poison traps (turn damage).
     */
    private static final int POISON_TRAP_DAMAGE = 2;

    /**
     * Damage dealt by teleport traps.
     */
    private static final int TELEPORT_TRAP_DAMAGE = 0;

    private Random random;

    /**
     * Creates a new EntityPopulator.
     */
    public EntityPopulatorImpl() {
        this.random = Dice.getRandom();
    }

    @Override
    public void populate(final GameMap map, final int levelNumber, final SpawnConfig config) {
        final List<Room> rooms = map.getRooms();

        // Skip first room (player spawn)
        for (int i = 1; i < rooms.size(); i++) {
            populateRoom(map, rooms.get(i), levelNumber, config);
        }
    }

    @Override
    public void setSeed(final long seed) {
        this.random = new Random(seed);
    }

    /**
     * Populates a single room with items, enemies, and traps.
     *
     * @param map the game map
     * @param room the room to populate
     * @param levelNumber the dungeon level
     * @param config spawn configuration
     */
    private void populateRoom(final GameMap map, final Room room,
                              final int levelNumber, final SpawnConfig config) {
        final List<Position> availablePositions = getFloorPositions(map, room);
        if (availablePositions.isEmpty()) {
            return;
        }

        // Spawn equipment
        spawnEquipment(map, room, availablePositions, config);

        // Spawn consumables
        spawnConsumables(map, room, availablePositions, levelNumber, config);

        // Spawn traps
        spawnTraps(map, room, availablePositions, levelNumber, config);

        // Spawn enemies
        spawnEnemies(map, availablePositions, levelNumber, config);
    }

    /**
     * Gets all floor positions within a room.
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

    /**
     * Spawns equipment in a room based on configuration.
     */
    private void spawnEquipment(final GameMap map, final Room room,
                                final List<Position> positions, final SpawnConfig config) {
        // Axe
        if (rollChance(config.axeRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item axe = new MeleeWeapon("Ascia", 8);
            map.addItem(pos, axe);
            addItemToRoom(room, axe);
        }

        // TODO: Spear

        // TODO: Bow

        // TODO: Ring

        // TODO: Armor
    }

    /**
     * Spawns consumable items based on configuration.
     */
    private void spawnConsumables(final GameMap map, final Room room,
                                  final List<Position> positions,
                                  final int levelNumber, final SpawnConfig config) {
        // Food/Potion (rate decreases with level)
        final double foodRate = config.getFoodPotionRate(levelNumber);
        if (rollChance(foodRate) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item potion = new HealthPotion();
            map.addItem(pos, potion);
            addItemToRoom(room, potion);
        }

        // TODO: Gold

        // TODO: Arrows
    }

    /**
     * Spawns traps based on level requirements.
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
        final TrapType type = availableTraps.get(random.nextInt(availableTraps.size()));
        final Position pos = pickRandomPosition(positions);
        final int damage = switch (type) {
            case SPIKE -> SPIKE_TRAP_DAMAGE;
            case POISON -> POISON_TRAP_DAMAGE;
            case TELEPORT -> TELEPORT_TRAP_DAMAGE;
        };

        final SimpleTrap trap = new SimpleTrap(pos, damage);
        addTrapToRoom(room, trap);

        map.setTileAt(pos, Tile.TRAP);
    }

    /**
     * Spawns enemies using weighted selection based on level.
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
     */
    private Enemy createWeightedEnemy(final Position pos, final int level, final SpawnConfig config) {
        // Calculate weights for each enemy type
        final int batWeight = config.getEnemyWeight(0, level);
        final int snakeWeight = config.getEnemyWeight(1, level);
        final int goblinWeight = config.getEnemyWeight(2, level);
        // final int golemWeight = config.getEnemyWeight(3, level);

        final int totalWeight = batWeight + snakeWeight + goblinWeight;
        int roll = random.nextInt(totalWeight);

        // Select enemy based on weight
        roll -= batWeight;
        if (roll < 0) {
            return new Bat(pos);
        }

        roll -= snakeWeight;
        if (roll < 0) {
            // TODO: Snake
            // return new Snake(pos);
        }

        return new Bat(pos);
    }

    /**
     * Rolls a probability check.
     *
     * @param probability the probability (0.0 to 1.0)
     * @return true if the roll succeeds
     */
    private boolean rollChance(final double probability) {
        return random.nextDouble() < probability;
    }

    /**
     * Picks and removes a random position from the list.
     */
    private Position pickRandomPosition(final List<Position> positions) {
        final int index = random.nextInt(positions.size());
        return positions.remove(index);
    }

    /**
     * Adds an item to a room (for room-level tracking).
     */
    private void addItemToRoom(final Room room, final Item item) {
        room.addItem(item);
    }

    /**
     * Adds a trap to a room.
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
