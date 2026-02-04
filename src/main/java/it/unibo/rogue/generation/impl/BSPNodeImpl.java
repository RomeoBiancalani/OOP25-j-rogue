package it.unibo.rogue.generation.impl;

import it.unibo.rogue.entity.Position;
import it.unibo.rogue.generation.api.BSPNode;
import it.unibo.rogue.world.api.Room;
import java.util.Optional;

/**
 * Implementation of a BSP tree node for dungeon generation.
 */
public final class BSPNodeImpl implements BSPNode {

    private final Position topLeft;
    private final int width;
    private final int height;
    private final BSPNode leftChild;
    private final BSPNode rightChild;
    private final boolean horizontalSplit;
    private Room room;

    /**
     * Creates a leaf node (no children).
     *
     * @param topLeft the top-left position
     * @param width the partition width
     * @param height the partition height
     */
    public BSPNodeImpl(final Position topLeft, final int width, final int height) {
        this(topLeft, width, height, null, null, false);
    }

    /**
     * Creates a node with children (internal node).
     *
     * @param topLeft the top-left position
     * @param width the partition width
     * @param height the partition height
     * @param leftChild the left child node
     * @param rightChild the right child node
     * @param horizontalSplit true if the split was horizontal
     */
    public BSPNodeImpl(
            final Position topLeft,
            final int width,
            final int height,
            final BSPNode leftChild,
            final BSPNode rightChild,
            final boolean horizontalSplit) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.horizontalSplit = horizontalSplit;
        this.room = null;
    }

    @Override
    public Position getTopLeft() {
        return topLeft;
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
    public Optional<BSPNode> getLeftChild() {
        return Optional.ofNullable(leftChild);
    }

    @Override
    public Optional<BSPNode> getRightChild() {
        return Optional.ofNullable(rightChild);
    }

    @Override
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    @Override
    public Optional<Room> getRoom() {
        return Optional.ofNullable(room);
    }

    @Override
    public boolean isHorizontalSplit() {
        return horizontalSplit;
    }

    @Override
    public Position getCenter() {
        return new Position(topLeft.x() + width / 2, topLeft.y() + height / 2);
    }

    /**
     * Sets the room for this partition.
     * Should only be called on leaf nodes.
     *
     * @param room the room to place in this partition
     */
    public void setRoom(final Room room) {
        this.room = room;
    }
}
