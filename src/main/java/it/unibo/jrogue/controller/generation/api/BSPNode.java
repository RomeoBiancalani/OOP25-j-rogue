package it.unibo.jrogue.controller.generation.api;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Room;

import java.util.Optional;

/**
 * Represents a node in the BSP (Binary Space Partitioning) tree
 * used for dungeon generation.
 */
public interface BSPNode {

    /**
     * Returns the top-left corner position of this partition.
     *
     * @return the top-left position
     */
    Position getTopLeft();

    /**
     * Returns the width of this partition in tiles.
     *
     * @return the partition width
     */
    int getWidth();

    /**
     * Returns the height of this partition in tiles.
     *
     * @return the partition height
     */
    int getHeight();

    /**
     * Returns the left child node, if any.
     *
     * @return the left child, or empty if this is a leaf
     */
    Optional<BSPNode> getLeftChild();

    /**
     * Returns the right child node, if any.
     *
     * @return the right child, or empty if this is a leaf
     */
    Optional<BSPNode> getRightChild();

    /**
     * Checks if this is a leaf node (has no children).
     *
     * @return true if this is a leaf
     */
    boolean isLeaf();

    /**
     * Returns the room placed in this partition.
     * Only leaf nodes can have rooms.
     *
     * @return the room, or empty if not a leaf or no room placed
     */
    Optional<Room> getRoom();

    /**
     * Checks if the split was horizontal.
     *
     * @return true if horizontal split, false if vertical
     */
    boolean isHorizontalSplit();

    /**
     * Returns the center position of this partition.
     *
     * @return the center position
     */
    Position getCenter();
}
