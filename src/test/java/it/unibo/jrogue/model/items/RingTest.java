package it.unibo.jrogue.model.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.jrogue.entity.items.impl.Ring;

/**
 * Test to verify the class Ring.
 */
class RingTest {

    private static final String EXPECTED_RING_NAME = "Healing ring";
    private static final int EXPECTED_HEALING = 10;

    @Test
    void testCreation() {
        final Ring ring = new Ring(EXPECTED_RING_NAME, EXPECTED_HEALING);
        assertEquals(EXPECTED_HEALING, ring.getBonus(), "The expected healing factor of the ring should be 10");
    }

    @Test
    void testIdentificationLogic() {
        final Ring ring = new Ring(EXPECTED_RING_NAME, EXPECTED_HEALING);

        assertNotEquals(EXPECTED_RING_NAME, ring.getName());
        assertEquals("Mysterious ring", ring.getName());
        assertTrue(ring.getDescription().contains("Mysterious"));

        ring.identify();

        assertEquals(EXPECTED_RING_NAME, ring.getName());
        assertTrue(ring.getDescription().contains(EXPECTED_RING_NAME));
        assertTrue(ring.getDescription().contains(String.valueOf(EXPECTED_HEALING)));
    }

    @Test
    void testIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ring(null, EXPECTED_HEALING);
        }, "The ring name can not be null");

        assertThrows(IllegalArgumentException.class, () -> {
            new Ring("", EXPECTED_HEALING);

        }, "The ring must have a name");
        assertThrows(IllegalArgumentException.class, () -> {
            new Ring(EXPECTED_RING_NAME, -1);
        }, "The healing factor of the ring must be positive");
    }
}
