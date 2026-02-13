package it.unibo.jrogue.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.impl.enemies.Dragon;

/**
 * TestClass for dragon test.
 */
class DragonTest {
    private static final Position START_POS = new Position(5, 5);
    private static final int D_LEVEL = 6;
    private static final int D_AC = 12;
    private static final int D_MIN_HP = 4;
    private static final int D_MAX_HP = 40;
    private static final int D_VISIBILITY = 5;

    private Dragon dragon;

    @BeforeEach
    void setUp() {
        dragon = new Dragon(START_POS);
    }

    @Test
    void testInitialization() {
        assertEquals(dragon.getPosition(), START_POS);
        assertEquals(dragon.getLevel(), D_LEVEL);
        assertEquals(dragon.getArmorClass(), D_AC);
        assertEquals(dragon.getMaxLifePoint(), dragon.getLifePoint());
        assertFalse(dragon.isSleeping());
        // HobGoblin hp should be within the 2-9 range
        assertTrue(dragon.getLifePoint() >= D_MIN_HP && dragon.getLifePoint() <= D_MAX_HP);
        //start position must be not null
        assertThrows(NullPointerException.class, () -> new Dragon(null));
    }

    @Test
    void testVisibility() {
        // HobGoblin has range 2 of visibility.
        final int bound = D_VISIBILITY + START_POS.x();
        for (int j = 0; j < bound; j++) {
            for (int i = 0; i < bound; i++) {
                assertTrue(dragon.canSeePlayer(new Position(i, j)));
            }
        }
    }

    @Test
    void testSleeping() {
        Dragon sleepingD;
        do {
            sleepingD = new Dragon(START_POS);
        } while (!sleepingD.isSleeping());
        assertTrue(sleepingD.isSleeping());
        // Bat can't move if is sleeping.
        assertEquals(Move.IDLE, sleepingD.getNextMove(new Position(3, 3)));
        sleepingD.wakeUp();
        assertFalse(sleepingD.isSleeping());
    }

    @Test
    void testMovementBehavior() {
        final Position visiblePlayerPos = new Position(1, 1);
        final Position notVisiblePlayerPos = new Position(20, 20);
        if (dragon.isSleeping()) {
            dragon.wakeUp();
        }
        // if bat doesn't see player it doesn't move.
        assertEquals(Move.IDLE, dragon.getNextMove(notVisiblePlayerPos));
        // if bat move towards to (1, 1), move has to be top-left (-1, -1).
        assertEquals(Move.TOP_LEFT, dragon.getNextMove(visiblePlayerPos));
    }

    @Test
    void testHealthLogic() {
        final int hp = dragon.getLifePoint();
        assertTrue(dragon.isAlive());
        // Damage must be non negative.
        assertThrows(IllegalArgumentException.class, () -> dragon.damage(-1));
        dragon.damage(1);
        assertEquals(dragon.getLifePoint(), hp - 1);
        final int maxDamage = 100;
        dragon.damage(maxDamage);
        assertFalse(dragon.isAlive());
    }
}
