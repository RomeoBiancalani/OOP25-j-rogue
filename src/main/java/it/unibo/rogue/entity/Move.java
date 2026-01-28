package it.unibo.rogue.entity;

public enum Move {

    UP(0, -1),
    DOWN(0,1),
    RIGHT(1,0),
    LEFT(-1,0),
    UPPER_LEFT(-1,-1),
    UPPER_RIGHT(1,-1),
    BOTTOM_LEFT(-1,1),
    BOTTOM_RIGHT(1,1);

    private final int x;
    private final int y;

    Move(final int x,final int y){
        this.x = x;
        this.y = y;
    }
    
    public Position applyToPosition(final Position p) {
        return new Position(p.x() + x, p.y() + y);
    }

}
