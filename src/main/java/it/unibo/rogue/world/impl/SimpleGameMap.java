package it.unibo.rogue.world.impl;

import it.unibo.rogue.entity.entities.api.Entity;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.world.api.GameMap;
import it.unibo.rogue.world.api.Hallway;
import it.unibo.rogue.world.api.Room;
import it.unibo.rogue.world.api.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return entities.stream().anyMatch(e -> !(e instanceof it.unibo.rogue.entity.Player));
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

    /**
     * Adds an entity to this map.
     * @param entity the entity to add
     */
    public void addEntity(final Entity entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from this map.
     * @param entity the entity to remove
     * @return true if the entity was removed
     */
    public boolean removeEntity(final Entity entity) {
        return entities.remove(entity);
    }

    /**
     * Sets a tile at the given position.
     * @param pos the position
     * @param tile the new tile
     */
    public void setTileAt(final Position pos, final Tile tile) {
        if (isInBounds(pos)) {
            tiles[pos.y()][pos.x()] = tile;
        }
    }

    /**
     * Explores all tiles within a radius of a position. (DEBUG Function)
     * @param center the center position
     * @param radius the radius in tiles
     */
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

    private boolean isInBounds(final Position pos) {
        return pos.x() >= 0 && pos.x() < width && pos.y() >= 0 && pos.y() < height;
    }
}
