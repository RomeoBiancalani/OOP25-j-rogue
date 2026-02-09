package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.world.api.Room;
import it.unibo.jrogue.entity.world.api.Trap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple implementation of a dungeon room.
 */
public final class SimpleRoom implements Room {

    private final Position topLeft;
    private final int width;
    private final int height;
    private final List<Item> items;
    private final List<Trap> traps;
    private final boolean hidden;

    /**
     * Creates a new room.
     *
     * @param topLeft the top-left corner position
     * @param width the room width
     * @param height the room height
     */
    public SimpleRoom(final Position topLeft, final int width, final int height) {
        this(topLeft, width, height, false);
    }

    /**
     * Creates a new room with hidden flag.
     *
     * @param topLeft the top-left corner position
     * @param width the room width
     * @param height the room height
     * @param hidden whether the room is hidden
     */
    public SimpleRoom(final Position topLeft, final int width, final int height, final boolean hidden) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.hidden = hidden;
        this.items = new ArrayList<>();
        this.traps = new ArrayList<>();
    }

    @Override
    public boolean hasItems() {
        return !items.isEmpty();
    }

    @Override
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public List<Trap> getTraps() {
        return Collections.unmodifiableList(traps);
    }

    @Override
    public Position getTopLeft() {
        return topLeft;
    }

    @Override
    public Position getBottomRight() {
        return new Position(topLeft.x() + width - 1, topLeft.y() + height - 1);
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
    public Position getCenter() {
        return new Position(topLeft.x() + width / 2, topLeft.y() + height / 2);
    }

    @Override
    public boolean contains(final Position pos) {
        return pos.x() >= topLeft.x()
            && pos.x() < topLeft.x() + width
            && pos.y() >= topLeft.y()
            && pos.y() < topLeft.y() + height;
    }

    @Override
    public void addItem(final Item item) {
        items.add(item);
    }

    @Override
    public void addTrap(final Trap trap) {
        traps.add(trap);
    }
}
