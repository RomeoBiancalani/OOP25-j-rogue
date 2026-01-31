package it.unibo.rogue.entity.entities.impl;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Entity;

public abstract class AbstractEntity implements Entity{

    private int lifePoint;
    private final int level;
    private int armorClass;
    private Position currentPosition;

    public AbstractEntity(final int lifePoint,
                          final int level,
                          final int armorClass,
                          final Position startPosition) {
                            
        this.lifePoint = lifePoint;
        this.level = level;
        this.armorClass = armorClass;
        this.currentPosition = startPosition;
    }

    @Override
    public Position getPosition() {
        return currentPosition;
    }

    @Override
    public boolean isAlive() {
        return lifePoint > 0;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void doMove(Move move) {
        currentPosition = move.applyToPosition(currentPosition);
    }

    @Override
    public int getArmorClass() {
        return armorClass;
    }

}
