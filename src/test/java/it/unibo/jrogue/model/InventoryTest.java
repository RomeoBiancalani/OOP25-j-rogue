package it.unibo.jrogue.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import it.unibo.jrogue.entity.items.impl.SimpleInventory;
import it.unibo.jrogue.entity.items.impl.HealthPotion;
import it.unibo.jrogue.entity.items.api.Inventory;
import it.unibo.jrogue.entity.items.api.Item;

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
