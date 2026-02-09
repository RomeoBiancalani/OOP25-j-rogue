package it.unibo.jrogue.entity.world.api;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.items.api.Item;
import java.util.List;

/**
 * Represents a room in the dungeon.
 */
public interface Room {

    /**
     * Checks if this room contains any items.
     *
     * @return true if the room has items
     */
    boolean hasItems();

    /**
     * Returns the list of items in this room.
     *
     * @return list of items
     */
    List<Item> getItems();

    /**
     * Checks if this is a hidden/secret room.
     *
     * @return true if the room is hidden
     */
    boolean isHidden();

    /**
     * Returns the list of traps in this room.
     *
     * @return list of traps
     */
    List<Trap> getTraps();

    /**
     * Returns the top-left corner position of the room.
     *
     * @return the top-left position
     */
    Position getTopLeft();

    /**
     * Returns the bottom-right corner position of the room.
     *
     * @return the bottom-right position
     */
    Position getBottomRight();

    /**
     * Returns the width of the room in tiles.
     *
     * @return the room width
     */
    int getWidth();

    /**
     * Returns the height of the room in tiles.
     *
     * @return the room height
     */
    int getHeight();

    /**
     * Returns the center position of the room.
     *
     * @return the center position
     */
    Position getCenter();

    /**
     * Checks if a position is inside this room.
     *
     * @param pos the position to check
     * @return true if the position is inside the room
     */
    boolean contains(Position pos);

    /**
     * Adds an item to this room.
     *
     * @param item the item to add
     */
    void addItem(Item item);

    /**
     * Adds a trap to this room.
     *
     * @param trap the trap to add
     */
    void addTrap(Trap trap);
}
