package it.unibo.jrogue.entity.world.api;

/**
 * Represents the different types of tiles in the game world.
 */
public enum Tile {
    /**
     * A solid wall tile that blocks movement.
     */
    WALL('#'),
    /**
     * A floor tile inside a room.
     */
    FLOOR('.'),
    /**
     * A corridor tile connecting rooms.
     */
    CORRIDOR(','),
    /**
     * A door tile between rooms and corridors.
     */
    DOOR('+'),
    /**
     * Stairs leading up to the next level.
     */
    STAIRS_UP('<'),
    /**
     * A trap tile.
     */
    TRAP('^'),
    /**
     * Empty void (outside the map).
     */
    VOID(' ');

    private final char symbol;

    Tile(final char symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the ASCII symbol representing this tile.
     *
     * @return the character symbol
     */
    public char getSymbol() {
        return symbol;
    }
}
