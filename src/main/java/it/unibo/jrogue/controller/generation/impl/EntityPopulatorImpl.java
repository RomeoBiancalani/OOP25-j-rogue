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
import it.unibo.jrogue.entity.items.impl.Amulet;
import it.unibo.jrogue.entity.items.impl.Armor;
import it.unibo.jrogue.entity.items.impl.Food;
import it.unibo.jrogue.entity.items.impl.Gold;
import it.unibo.jrogue.entity.items.impl.HealthPotion;
import it.unibo.jrogue.entity.items.impl.MeleeWeapon;
import it.unibo.jrogue.entity.items.impl.Ring;
import it.unibo.jrogue.entity.items.impl.Scroll;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Room;
import it.unibo.jrogue.entity.world.api.Tile;
import it.unibo.jrogue.entity.world.impl.SimpleTrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of EntityPopulator that populates rooms with
 * items, enemies, and traps based on configuration.
 */
public final class EntityPopulatorImpl implements EntityPopulator {

    private static final int SPIKE_TRAP_DAMAGE = 5;
    private static final int POISON_TRAP_DAMAGE = 2;
    private static final int TELEPORT_TRAP_DAMAGE = 0;
    private static final int BASE_GOLD_AMOUNT = 10;
    private static final int GOLD_RANDOM_BOUND = 20;
    private static final int BASE_RING_HEALING = 2;
    private static final int RING_HEALING_BOUND = 5;
    private static final int LIGHT_ARMOR_PROTECTION = 1;
    private static final int HEAVY_ARMOR_PROTECTION = 3;
    private static final String LIGHT_ARMOR_NAME = "Armatura di cuoio";
    private static final String HEAVY_ARMOR_NAME = "Armatura di ferro";
    private static final String RING_NAME = "Anello recupera vita";

    private static final int DAGGER_BASE_DAMAGE = 5;
    private static final int SWORD_BASE_DAMAGE = 8;
    private static final int SHOVEL_BASE_DAMAGE = 10;

    /**
     * Creates a new EntityPopulator.
     */
    public EntityPopulatorImpl() {
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
                map.addItem(pos, new Amulet());
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

        spawnEquipment(map, room, availablePositions, levelNumber, config);
        spawnConsumables(map, room, availablePositions, levelNumber, config);
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

    /**
     * Spawns equipment in a room based on configuration.
     *
     * @param map         the game map
     * @param room        the room to spawn equipment in
     * @param positions   available positions for spawning
     * @param levelNumber the dungeon level
     * @param config      spawn configuration
     */
    private void spawnEquipment(final GameMap map, final Room room,
            final List<Position> positions,
            final int levelNumber, final SpawnConfig config) {
        // Weapons gets better in next levels so it's suggested to swap (even the same
        // weapon)

        // Dagger (Weapon 1)
        if (rollChance(config.daggerRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item axe = new MeleeWeapon("Pugnale", DAGGER_BASE_DAMAGE + levelNumber);
            map.addItem(pos, axe);
            addItemToRoom(room, axe);
        }

        // Sword (Weapon 2)
        if (rollChance(config.swordRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item axe = new MeleeWeapon("Spada", SWORD_BASE_DAMAGE + levelNumber);
            map.addItem(pos, axe);
            addItemToRoom(room, axe);
        }

        // Shovel (Weapon 3)
        if (rollChance(config.shovelRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item axe = new MeleeWeapon("Pala", SHOVEL_BASE_DAMAGE + levelNumber);
            map.addItem(pos, axe);
            addItemToRoom(room, axe);
        }

        // Armor
        if (rollChance(config.armorRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item armor;
            if (GameRandom.nextDouble() < config.armor1Ratio()) {
                armor = new Armor(LIGHT_ARMOR_NAME, LIGHT_ARMOR_PROTECTION + levelNumber / 2);
            } else {
                armor = new Armor(HEAVY_ARMOR_NAME, HEAVY_ARMOR_PROTECTION + levelNumber);
            }
            map.addItem(pos, armor);
            addItemToRoom(room, armor);
        }

        // Ring
        if (rollChance(config.ringRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final int healing = BASE_RING_HEALING + GameRandom.nextInt(RING_HEALING_BOUND);
            final Item ring = new Ring(RING_NAME, healing);
            map.addItem(pos, ring);
            addItemToRoom(room, ring);
        }
    }

    /**
     * Spawns consumable items based on configuration.
     *
     * @param map         the game map
     * @param room        the room to spawn consumables in
     * @param positions   available positions for spawning
     * @param levelNumber the dungeon level
     * @param config      spawn configuration
     */
    private void spawnConsumables(final GameMap map, final Room room,
            final List<Position> positions,
            final int levelNumber, final SpawnConfig config) {
        // Health Potion (rate decreases with level)
        final double potionRate = config.getFoodPotionRate(levelNumber);
        if (rollChance(potionRate) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item potion = new HealthPotion();
            map.addItem(pos, potion);
            addItemToRoom(room, potion);
        }

        // Food
        if (rollChance(config.foodRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item food = new Food();
            map.addItem(pos, food);
            addItemToRoom(room, food);
        }

        // Gold
        if (rollChance(config.goldRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final int amount = BASE_GOLD_AMOUNT + GameRandom.nextInt(GOLD_RANDOM_BOUND) + 1;
            final Item gold = new Gold(amount);
            map.addItem(pos, gold);
            addItemToRoom(room, gold);
        }

        // Scroll
        if (rollChance(config.scrollRate()) && !positions.isEmpty()) {
            final Position pos = pickRandomPosition(positions);
            final Item scroll = new Scroll();
            map.addItem(pos, scroll);
            addItemToRoom(room, scroll);
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
