package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Hallway;
import it.unibo.jrogue.entity.world.api.Room;

import java.util.Collections;
import java.util.List;

/**
 * Simple implementation of a dungeon hallway/corridor.
 */
public final class SimpleHallway implements Hallway {

    private final List<Position> path;
    private final List<Room> connectedRooms;
    private boolean hidden;

    /**
     * Creates a new hallway.
     *
     * @param path the positions forming the hallway
     * @param connectedRooms the rooms this hallway connects
     */
    public SimpleHallway(final List<Position> path, final List<Room> connectedRooms) {
        this(path, connectedRooms, true);
    }

    /**
     * Creates a new hallway with hidden flag.
     *
     * @param path the positions forming the hallway
     * @param connectedRooms the rooms this hallway connects
     * @param hidden whether the hallway is hidden
     */
    public SimpleHallway(final List<Position> path, final List<Room> connectedRooms, final boolean hidden) {
        this.path = List.copyOf(path);
        this.connectedRooms = List.copyOf(connectedRooms);
        this.hidden = hidden;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void reveal() {
        this.hidden = false;
    }

    @Override
    public List<Position> getPath() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public List<Room> getConnectedRooms() {
        return Collections.unmodifiableList(connectedRooms);
    }
}
