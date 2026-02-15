package it.unibo.jrogue.entity.world.api;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.api.Entity;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Item;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the map of a single dungeon level.
 * Named GameMap to avoid collision with java.util.Map.
 */
public interface GameMap {

    /**
     * Checks if there are enemies on this map.
     *
     * @return true if enemies exist on the map
     */
    boolean hasEnemies();

    /**
     * Checks if a position is walkable (not a wall or void).
     *
     * @param pos the position to check
     * @return true if the position can be walked on
     */
    boolean isWalkable(Position pos);

    /**
     * Returns the map width in tiles.
     *
     * @return the width
     */
    int getWidth();

    /**
     * Returns the map height in tiles.
     *
     * @return the height
     */
    int getHeight();

    /**
     * Returns the tile at the given position.
     *
     * @param pos the position to query
     * @return the tile at that position
     */
    Tile getTileAt(Position pos);

    /**
     * Returns all rooms in this map.
     *
     * @return list of rooms
     */
    List<Room> getRooms();

    /**
     * Returns all hallways in this map.
     *
     * @return list of hallways
     */
    List<Hallway> getHallways();

    /**
     * Returns all entities currently on this map.
     *
     * @return list of entities
     */
    List<Entity> getEntities();

    /**
     * Returns the player starting position.
     *
     * @return the starting position
     */
    Position getStartingPosition();

    /**
     * Returns the position of stairs up to the next level.
     *
     * @return the stairs position, or empty if none
     */
    Optional<Position> getStairsUp();

    /**
     * Returns the entity at a given position, if any.
     *
     * @param pos the position to check
     * @return the entity at that position, or empty
     */
    Optional<Entity> getEntityAt(Position pos);

    /**
     * Returns the player on this map.
     *
     * @return the player, or empty if not yet set
     */
    Optional<Player> getPlayer();

    /**
     * Returns all enemies on this map.
     *
     * @return list of enemies
     */
    List<Enemy> getEnemies();

    /**
     * Returns all items on this map by position.
     *
     * @return map of position to item
     */
    Map<Position, Item> getItems();

    /**
     * Returns all wall positions on this map.
     *
     * @return set of wall positions
     */
    Set<Position> getWallPositions();

    /**
     * Sets the player on this map.
     *
     * @param player the player entity
     */
    void setPlayer(Player player);

    /**
     * Adds an item at the given position.
     *
     * @param pos the position
     * @param item the item to add
     */
    void addItem(Position pos, Item item);

    /**
     * Removes and returns the item at the given position.
     *
     * @param pos the position
     * @return the removed item, or empty if none
     */
    Optional<Item> removeItemAt(Position pos);

    /**
     * Places a trap at the specified position on the map.
     *
     * @param pos  the position where the trap should be placed.
     * @param trap the trap to place.
     */
    void addTrap(Position pos, Trap trap);

    /**
     * Returns the trap at the specified position, if any.
     *
     * @param pos the position to check.
     * @return an Optional containing the trap if present, empty otherwise.
     */
    Optional<Trap> getTrapAt(Position pos);

    /**
     * Removes and returns the trap at the specified position, if any.
     *
     * @param pos the position from which to remove the trap.
     * @return an Optional containing the removed trap if present, empty otherwise.
     */
    Optional<Trap> removeTrapAt(Position pos);

    /**
     * Returns an unmodifiable view of all traps currently on the map.
     *
     * @return a map of positions to their associated traps.
     */
    Map<Position, Trap> getTraps();

    /**
     * Adds an entity to this map.
     *
     * @param entity the entity to add
     */
    void addEntity(Entity entity);

    /**
     * Removes an entity from this map.
     *
     * @param entity the entity to remove
     * @return true if the entity was removed
     */
    boolean removeEntity(Entity entity);

    /**
     * Sets a tile at the given position.
     *
     * @param pos the position
     * @param tile the new tile
     */
    void setTileAt(Position pos, Tile tile);
}
