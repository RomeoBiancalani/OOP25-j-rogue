package it.unibo.rogue.entity.world.impl;

import it.unibo.rogue.commons.Position;
import it.unibo.rogue.entity.gameEntities.api.Enemy;
import it.unibo.rogue.entity.gameEntities.api.Entity;
import it.unibo.rogue.entity.gameEntities.api.Player;
import it.unibo.rogue.entity.items.api.Item;
import it.unibo.rogue.entity.world.api.GameMap;
import it.unibo.rogue.entity.world.api.Hallway;
import it.unibo.rogue.entity.world.api.Room;
import it.unibo.rogue.entity.world.api.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple implementation of a dungeon map.
 */
public final class SimpleGameMap implements GameMap {

    private final Tile[][] tiles;
    private final int width;
    private final int height;
    private final List<Room> rooms;
    private final List<Hallway> hallways;
    private final List<Entity> entities;
    private final Set<Position> explored;
    private final Position startingPosition;
    private final Position stairsDown;
    private final Map<Position, Item> itemPositions;
    private Player player;
    private Set<Position> wallCache;

    /**
     * Creates a new game map.
     * @param tiles the 2D tile array [y][x]
     * @param rooms the rooms in this map
     * @param hallways the hallways connecting rooms
     * @param startingPosition the player starting position
     * @param stairsDown the position of stairs to next level
     */
    public SimpleGameMap(
            final Tile[][] tiles,
            final List<Room> rooms,
            final List<Hallway> hallways,
            final Position startingPosition,
            final Position stairsDown) {
        this.height = tiles.length;
        this.width = tiles.length > 0 ? tiles[0].length : 0;
        this.tiles = copyTiles(tiles);
        this.rooms = List.copyOf(rooms);
        this.hallways = List.copyOf(hallways);
        this.entities = new ArrayList<>();
        this.explored = new HashSet<>();
        this.startingPosition = startingPosition;
        this.stairsDown = stairsDown;
        this.itemPositions = new HashMap<>();
    }

    private Tile[][] copyTiles(final Tile[][] original) {
        final Tile[][] copy = new Tile[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    @Override
    public boolean hasEnemies() {
        return entities.stream().anyMatch(e -> e instanceof Enemy);
    }

    @Override
    public boolean isWalkable(final Position pos) {
        if (!isInBounds(pos)) {
            return false;
        }
        final Tile tile = getTileAt(pos);
        return tile == Tile.FLOOR
            || tile == Tile.CORRIDOR
            || tile == Tile.DOOR
            || tile == Tile.STAIRS_DOWN
            || tile == Tile.STAIRS_UP;
    }

    @Override
    public boolean isExplored(final Position pos) {
        return explored.contains(pos);
    }

    @Override
    public void explore(final Position pos) {
        // TODO: Decide if fog needs to be implemented. Otherwise this is not needed
        explored.add(pos);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Tile getTileAt(final Position pos) {
        if (!isInBounds(pos)) {
            return Tile.VOID;
        }
        return tiles[pos.y()][pos.x()];
    }

    @Override
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    @Override
    public List<Hallway> getHallways() {
        return Collections.unmodifiableList(hallways);
    }

    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    @Override
    public Position getStartingPosition() {
        return startingPosition;
    }

    @Override
    public Optional<Position> getStairsDown() {
        return Optional.ofNullable(stairsDown);
    }

    @Override
    public Optional<Entity> getEntityAt(final Position pos) {
        return entities.stream()
            .filter(e -> e.getPosition().equals(pos))
            .findFirst();
    }

    @Override
    public void addEntity(final Entity entity) {
        entities.add(entity);
    }

    @Override
    public boolean removeEntity(final Entity entity) {
        return entities.remove(entity);
    }

    @Override
    public void setTileAt(final Position pos, final Tile tile) {
        if (isInBounds(pos)) {
            tiles[pos.y()][pos.x()] = tile;
        }
    }

    @Override
    public void exploreRadius(final Position center, final int radius) {
        // -radius ... +radius
        for (int dy = -radius; dy <= radius; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                final Position pos = new Position(center.x() + dx, center.y() + dy);
                if (isInBounds(pos)) {
                    explore(pos);
                }
            }
        }
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    @Override
    public List<Enemy> getEnemies() {
        return entities.stream()
            .filter(e -> e instanceof Enemy)
            .map(e -> (Enemy) e)
            .collect(Collectors.toList());
    }

    @Override
    public Map<Position, Item> getItems() {
        return Collections.unmodifiableMap(itemPositions);
    }

    @Override
    public Set<Position> getWallPositions() {
        if (wallCache == null) {
            buildWallCache();
        }
        return Collections.unmodifiableSet(wallCache);
    }

    @Override
    public void setPlayer(final Player player) {
        this.player = player;
    }

    @Override
    public void addItem(final Position pos, final Item item) {
        itemPositions.put(pos, item);
    }

    @Override
    public Optional<Item> removeItemAt(final Position pos) {
        return Optional.ofNullable(itemPositions.remove(pos));
    }

    /**
     * Builds the wall position cache by scanning all tiles.
     */
    private void buildWallCache() {
        wallCache = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x] == Tile.WALL) {
                    wallCache.add(new Position(x, y));
                }
            }
        }
    }

    private boolean isInBounds(final Position pos) {
        return pos.x() >= 0 && pos.x() < width && pos.y() >= 0 && pos.y() < height;
    }
}
