package it.unibo.rogue.entity.entities.impl.Enemies;

import it.unibo.rogue.entity.Dice;
import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.impl.AbstractEnemy;

public class HobGoblin extends AbstractEnemy{

    private static final int VISIBILITY = 2;

    public HobGoblin(Position starPosition) {
        super(starPosition, 2, Dice.roll(2, 9), 11);
    }

    @Override
    public Move getNextMove(Position playerPosition) {
        if (isSleeping()) {
            return Move.IDLE;
        }
        return moveTorward(playerPosition);
    }

    @Override
    public boolean canSeePlayer(Position playerPosition) {
        return getVisiblePositions(VISIBILITY).stream().anyMatch(p -> p.equals(playerPosition));
    }

    
}
