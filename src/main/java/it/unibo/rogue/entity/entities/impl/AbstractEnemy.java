package it.unibo.rogue.entity.entities.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Enemy;

public abstract class AbstractEnemy extends AbstractEntity implements Enemy{
    
    private static final int SLEEP_CHANCHE = 10;

    private static final Random rand = new Random();
    private boolean sleeping;

    public AbstractEnemy(final Position currentPosition,
                         final int level,
                         final int lifePoint,
                         final int armorClass) {

            super(lifePoint, level, armorClass, currentPosition);
            this.sleeping = computeSleeping();
    }

    @Override
    public boolean isSleeping() {
        return sleeping;
    }

    @Override
    public void wakeUp() {
        sleeping = false;
    }

    @Override
    public boolean computeSleeping() {
        return rand.nextInt(SLEEP_CHANCHE) == 0;
    }

    protected List<Position> getVisiblePositions(int visibility) {
        List<Position> visible = new LinkedList<>();
        Position batPosition = super.getPosition();
        for (int j = batPosition.y() - visibility; j <= batPosition.y() + visibility; j++) {
            for (int i = batPosition.x() - visibility; i <= batPosition.x() + visibility; i++) {
                visible.add(new Position(i, j));
            }
        }
        return Collections.unmodifiableList(visible);
    }

    protected static Random getRandom() {
        return rand;
    }

    protected Move randomMove() {
        Move[] moves = Move.values();
        return moves[rand.nextInt(moves.length)];
    }

    protected Move moveTorward(Position targetPosition) {
        Position from = getPosition();
        int dx = Integer.compare(targetPosition.x(), from.x());
        int dy = Integer.compare(targetPosition.y(), from.y());

        for (Move move : Move.values()) {
            if (move.getX() == dx && move.getY() == dy) {
                return move;
            }
        }

        return randomMove();
    }

}
