package it.unibo.rogue.world.api;

import it.unibo.rogue.entity.Position;
import java.util.List;

/**
 * Represents a corridor/hallway connecting rooms in the dungeon.
 */
public interface Hallway {

    /**
     * Checks if this is a hidden/secret passage.
     * @return true if the hallway is hidden
     */
    boolean isHidden();

    /**
     * Returns all positions that make up this hallway.
     * @return list of positions forming the hallway path
     */
    List<Position> getPath();

    /**
     * Returns the rooms this hallway connects.
     * @return list of connected rooms
     */
    List<Room> getConnectedRooms();
}
