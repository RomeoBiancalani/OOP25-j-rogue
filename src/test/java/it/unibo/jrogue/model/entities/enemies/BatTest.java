package it.unibo.jrogue.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.impl.enemies.Bat;

/**
 * Test class for Bat enemy.
 */
class BatTest {

    private static final Position START_POS = new Position(1, 1);
    private static final int BAT_LEVEL = 1;
    private static final int BAT_AC = 3;
    private static final int BAT_MIN_HP = 1;
    private static final int BAT_MAX_HP = 8;

    private Bat bat;

    @BeforeEach
    void setUp() {
        bat = new Bat(START_POS);
    }

    @Test
    void testInitialization() {
        assertEquals(bat.getPosition(), START_POS);
        assertEquals(bat.getLevel(), BAT_LEVEL);
        assertEquals(bat.getArmorClass(), BAT_AC);
        assertEquals(bat.getMaxLifePoint(), bat.getLifePoint());
        // Bat hp should be within the 1-8 range
        assertTrue(bat.getLifePoint() >= BAT_MIN_HP && bat.getLifePoint() <= BAT_MAX_HP);
        //start position must be not null
        assertThrows(NullPointerException.class, () -> new Bat(null));
    }

    @Test
    void testVisibility() {
        // Bat has range 6 of visibility.
        final int bound = 6 + START_POS.x();
        for (int j = 0; j < bound; j++) {
            for (int i = 0; i < bound; i++) {
                assertTrue(bat.canSeePlayer(new Position(i, j)));
            }
        }
    }

    @Test
    void testSleeping() {
        Bat sleepingBat;
        do {
            sleepingBat = new Bat(START_POS);
        } while (!sleepingBat.isSleeping());
        assertTrue(sleepingBat.isSleeping());
        // Bat can't move if is sleeping.
        assertEquals(Move.IDLE, sleepingBat.getNextMove(new Position(0, 0)));
        sleepingBat.wakeUp();
        assertFalse(sleepingBat.isSleeping());
    }

    @Test
    void testMovementBehavior() {
        if (bat.isSleeping()) {
            bat.wakeUp();
        }
        assertNotNull(bat.getNextMove(new Position(0, 0)));
    }

    @Test
    void testHealthLogic() {
        final int hp = bat.getLifePoint();
        assertTrue(bat.isAlive());
        // Damage must be non negative.
        assertThrows(IllegalArgumentException.class, () -> bat.damage(-1));
        if (hp > 1) {
            bat.damage(1);
            assertEquals(bat.getLifePoint(), hp - 1);
        }
        final int maxDamage = 100;
        bat.damage(maxDamage);
        assertFalse(bat.isAlive());
    }

}
