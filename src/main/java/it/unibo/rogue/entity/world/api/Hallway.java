package it.unibo.rogue.entity.world.api;

import java.util.List;

import it.unibo.rogue.commons.Position;

/**
 * Represents a corridor/hallway connecting rooms in the dungeon.
 */
public interface Hallway {

    /**
     * Checks if this is a hidden/secret passage.
     *
     * @return true if the hallway is hidden
     */
    boolean isHidden();

    /**
     * Returns all positions that make up this hallway.
     *
     * @return list of positions forming the hallway path
     */
    List<Position> getPath();

    /**
     * Returns the rooms this hallway connects.
     *
     * @return list of connected rooms
     */
    List<Room> getConnectedRooms();
}
