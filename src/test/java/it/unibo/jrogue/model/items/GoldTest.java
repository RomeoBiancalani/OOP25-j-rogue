package it.unibo.jrogue.model.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.jrogue.entity.items.impl.Gold;

/**
 * Test to verify the creation of Gold rightfully.
 */
class GoldTest {
    private static final int AMOUNT = 50;

    @Test
    void testGoldCreation() {
        final Gold gold = new Gold(AMOUNT);
        assertEquals(AMOUNT, gold.getAmount(), "The quantity expected is " + AMOUNT);
        assertTrue(gold.getDescription().contains(String.valueOf(AMOUNT)));
    }

    @Test
    void testIllegalCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Gold(-1);
        }, "The amount of gold must be positive");
        assertThrows(IllegalArgumentException.class, () -> {
            new Gold(0);
        }, "The amount of gold can not be 0");
    }
}
