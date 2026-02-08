package it.unibo.rogue.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.rogue.entity.items.impl.Gold;

/**
 * Test to verify the creation of Gold rightfully.
 */
class GoldTest {
    private static final int AMOUNT = 50;

    @Test
    void testGoldCreation() {
        final Gold gold = new Gold(AMOUNT);
        assertEquals(AMOUNT, gold.getAmount(), "La quantità dovrebbe essere 50");
        assertTrue(gold.getDescription().contains("50"));
    }

    @Test
    void testIllegalCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Gold(-1);
        }, "La quantità dell'oro non può essere negativa");
        assertThrows(IllegalArgumentException.class, () -> {
            new Gold(0);
        }, "La quantità dell'oro non può essere uguale a 0");
    }
}
