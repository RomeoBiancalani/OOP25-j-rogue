package it.unibo.rogue.world.api;

import it.unibo.rogue.entity.Entity;
import it.unibo.rogue.entity.Position;
import java.util.List;
import java.util.Optional;

/**
 * Represents the map of a single dungeon level.
 * Named GameMap to avoid collision with java.util.Map.
 */
public interface GameMap {

    /**
     * Checks if there are enemies on this map.
     * @return true if enemies exist on the map
     */
    boolean hasEnemies();

    /**
     * Checks if a position is walkable (not a wall or void).
     * @param pos the position to check
     * @return true if the position can be walked on
     */
    boolean isWalkable(Position pos);

    /**
     * Checks if a position has been explored by the player.
     * @param pos the position to check
     * @return true if the position has been explored
     */
    boolean isExplored(Position pos);

    /**
     * Marks a position as explored.
     * @param pos the position to mark
     */
    void explore(Position pos);

    /**
     * Returns the map width in tiles.
     * @return the width
     */
    int getWidth();

    /**
     * Returns the map height in tiles.
     * @return the height
     */
    int getHeight();

    /**
     * Returns the tile at the given position.
     * @param pos the position to query
     * @return the tile at that position
     */
    Tile getTileAt(Position pos);

    /**
     * Returns all rooms in this map.
     * @return list of rooms
     */
    List<Room> getRooms();

    /**
     * Returns all hallways in this map.
     * @return list of hallways
     */
    List<Hallway> getHallways();

    /**
     * Returns all entities currently on this map.
     * @return list of entities
     */
    List<Entity> getEntities();

    /**
     * Returns the player starting position.
     * @return the starting position
     */
    Position getStartingPosition();

    /**
     * Returns the position of stairs to the next level.
     * @return the stairs position, or empty if none
     */
    Optional<Position> getStairsDown();

    /**
     * Returns the entity at a given position, if any.
     * @param pos the position to check
     * @return the entity at that position, or empty
     */
    Optional<Entity> getEntityAt(Position pos);
}
