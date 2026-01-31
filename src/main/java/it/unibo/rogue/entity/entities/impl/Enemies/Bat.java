package it.unibo.rogue.entity.entities.impl.Enemies;

import it.unibo.rogue.entity.Dice;
import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.impl.AbstractEnemy;

public class Bat extends AbstractEnemy{

    private static final int VISIBILITY = 1;
    private static final int CHASE_PLAYER_PERCENT = 60;

    public Bat(Position startPosition) {
        super(startPosition, 1, Dice.roll(1,8), 3);
    }

	@Override
	public Move getNextMove(Position playerPosition) {
        if (!isSleeping()) {
            if (getRandom().nextInt(100) < CHASE_PLAYER_PERCENT) {
                return moveTorward(playerPosition);
            }
        }
        return Move.IDLE;
	}

    @Override
    public boolean canSeePlayer(Position playerPosition) {
        return getVisiblePositions(VISIBILITY).stream().anyMatch(p -> p.equals(playerPosition));
    }

}
