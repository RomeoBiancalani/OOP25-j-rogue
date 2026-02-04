package it.unibo.jrogue.model;

import static org.junit.jupiter.api.Assertions.*;

import it.unibo.jrogue.items.api.Inventory;
import it.unibo.jrogue.items.api.Item;
import it.unibo.jrogue.items.impl.HealthPotion;
import it.unibo.jrogue.items.impl.SimpleInventory;
import org.junit.jupiter.api.Test;


public class InventoryTest {
    @Test
    void testInventoryLimit() {
        Inventory inv = new SimpleInventory(1);
        Item potion = new HealthPotion();

        assertTrue(inv.addItem(potion), "il primo oggetto deve entrare");
        assertTrue(inv.isFull() , "ora dovrebbe essere pieno");


    }


    
}
