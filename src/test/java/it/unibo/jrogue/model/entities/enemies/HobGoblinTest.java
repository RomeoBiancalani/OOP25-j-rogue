package it.unibo.jrogue.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.impl.enemies.HobGoblin;

/**
 * TestClass for HobGoblin test.
 */
class HobGoblinTest {
    private static final Position START_POS = new Position(2, 2);
    private static final int HG_LEVEL = 2;
    private static final int HG_AC = 6;
    private static final int HG_MIN_HP = 2;
    private static final int HG_MAX_HP = 18;
    private static final int HG_VISIBILITY = 2;

    private HobGoblin hGoblin;

    @BeforeEach
    void setUp() {
        hGoblin = new HobGoblin(START_POS);
    }

    @Test
    void testInitialization() {
        assertEquals(hGoblin.getPosition(), START_POS);
        assertEquals(hGoblin.getLevel(), HG_LEVEL);
        assertEquals(hGoblin.getArmorClass(), HG_AC);
        assertEquals(hGoblin.getMaxLifePoint(), hGoblin.getLifePoint());
        // HobGoblin hp should be within the 2-9 range
        assertTrue(hGoblin.getLifePoint() >= HG_MIN_HP && hGoblin.getLifePoint() <= HG_MAX_HP);
        //start position must be not null
        assertThrows(NullPointerException.class, () -> new HobGoblin(null));
    }

    @Test
    void testVisibility() {
        // HobGoblin has range 2 of visibility.
        final int bound = HG_VISIBILITY + START_POS.x();
        for (int j = 0; j < bound; j++) {
            for (int i = 0; i < bound; i++) {
                assertTrue(hGoblin.canSeePlayer(new Position(i, j)));
            }
        }
    }

    @Test
    void testSleeping() {
        HobGoblin sleepingHG;
        do {
            sleepingHG = new HobGoblin(START_POS);
        } while (!sleepingHG.isSleeping());
        assertTrue(sleepingHG.isSleeping());
        // Bat can't move if is sleeping.
        assertEquals(Move.IDLE, sleepingHG.getNextMove(new Position(0, 0)));
        sleepingHG.wakeUp();
        assertFalse(sleepingHG.isSleeping());
    }

    @Test
    void testMovementBehavior() {
        final Position visiblePlayerPos = new Position(1, 1);
        final Position notVisiblePlayerPos = new Position(5, 5);
        if (hGoblin.isSleeping()) {
            hGoblin.wakeUp();
        }
        // if bat doesn't see player it doesn't move.
        assertEquals(Move.IDLE, hGoblin.getNextMove(notVisiblePlayerPos));
        // if bat move towards to (1, 1), move has to be top-left (-1, -1).
        assertEquals(Move.TOP_LEFT, hGoblin.getNextMove(visiblePlayerPos));
    }

    @Test
    void testHealthLogic() {
        final int hp = hGoblin.getLifePoint();
        assertTrue(hGoblin.isAlive());
        // Damage must be non negative.
        assertThrows(IllegalArgumentException.class, () -> hGoblin.damage(-1));
        hGoblin.damage(1);
        assertEquals(hGoblin.getLifePoint(), hp - 1);
        final int maxDamage = 100;
        hGoblin.damage(maxDamage);
        assertFalse(hGoblin.isAlive());
    }
}
