package it.unibo.rogue.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import it.unibo.rogue.entity.items.api.Inventory;
import it.unibo.rogue.entity.items.api.Item;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.SimpleInventory;

/**
 * Test for Inventory.
 */
class InventoryTest {
    private static final int EXPECTED_SIZE = 5;

    @Test
    void testInventoryCreation() {
        final Inventory inventory = new SimpleInventory(EXPECTED_SIZE);
        assertEquals(EXPECTED_SIZE, inventory.getSize(), "La dimensione dovrebbe essere 5");
    }

    @Test
    void testInvalidCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SimpleInventory(0);
        }, "Non si deve poter creare un inventario di dimensione 0");

        assertThrows(IllegalArgumentException.class, () -> {
            new SimpleInventory(-1);
        }, "Non si dovrebbe poter creare un inventario di dimensione negativa");
    }

    @Test
    void testInventoryIsFull() {
        final Inventory inventory = new SimpleInventory(EXPECTED_SIZE);
        final Item testItem = new Armor("sasso", 0);
        for (int i = 0; i < EXPECTED_SIZE; i++) {
            inventory.addItem(testItem);
        }

        assertThrows(IllegalStateException.class, () -> {
            inventory.addItem(testItem);
        }, "L'inventario è pieno, non si può aggiungere nient'altro");
    }

    @Test
    void testItemExistsInInventory() {
        final Inventory inventory = new SimpleInventory(EXPECTED_SIZE);
        final Item testItem = new Armor("sasso", 0);

        assertTrue(inventory.getItem(0).isEmpty(), "Lo slot 0 è vuoto");
        inventory.addItem(testItem);
        assertTrue(inventory.getItem(0).isPresent(), "ci dovrebbe essere un item qui");
        assertEquals(testItem, inventory.getItem(0).get(), "L'oggetto nello slot 0 deve essere il sasso");
    }
}
