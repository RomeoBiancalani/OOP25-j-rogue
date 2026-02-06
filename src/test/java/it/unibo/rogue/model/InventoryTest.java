package it.unibo.rogue.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import it.unibo.rogue.entity.items.impl.SimpleInventory;
import it.unibo.rogue.entity.items.impl.HealthPotion;
import it.unibo.rogue.entity.items.api.Inventory;
import it.unibo.rogue.entity.items.api.Item;

/**
 * Tests for the Inventory functionality.
 */
class InventoryTest {

    @Test
    void testInventoryLimit() {
        final Inventory inv = new SimpleInventory(1);
        final Item potion = new HealthPotion();

        assertTrue(inv.addItem(potion), "il primo oggetto deve entrare");
        assertTrue(inv.isFull(), "ora dovrebbe essere pieno");
    }
}
