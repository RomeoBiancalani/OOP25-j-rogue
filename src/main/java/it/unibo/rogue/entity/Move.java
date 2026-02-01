package it.unibo.rogue.entity;

/**
 * Represents the possible mmovement in the game grid.
 */
public enum Move {

    UP(0, -1), 
    DOWN(0, 1), 
    RIGHT(1, 0), 
    LEFT(-1, 0), 
    TOP_LEFT(-1, -1), 
    TOP_RIGHT(1, -1), 
    BOTTOM_LEFT(-1, 1), 
    BOTTOM_RIGHT(1, 1), 
    IDLE(0, 0);

    private final int x;
    private final int y;

    /**
     * Internal constructor for move offsets.
     * 
     * @param x The horrizontal offset.
     * @param y The vertical offset.
     */
    Move(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculate a new position by applying this move to a position.
     * 
     * @param p The starting position.
     * @return A new position representing the coordinates after the move.
     * @throws IllegalArgumentException if p is null.
     */
    public Position applyToPosition(final Position p) {
        if (p == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        return new Position(p.x() + x, p.y() + y);
    }

    /**
     * @return the horizontal coordinate offset.
     */
    public int getX() {
        return x;
    }

    /**
     * @return the vertical coordinate offset.
     */
    public int getY() {
        return y;
    }

}
