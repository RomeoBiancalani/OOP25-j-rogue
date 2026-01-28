package it.unibo.rogue.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import it.unibo.rogue.entity.items.api.*;
import it.unibo.rogue.entity.items.impl.*;


public class InventoryTest {
    @Test
    void testInventoryLimit() {
        Inventory inv = new SimpleInventory(1);
        Item potion = new HealthPotion();

        assertTrue(inv.addItem(potion), "il primo oggetto deve entrare");
        assertTrue(inv.isFull() , "ora dovrebbe essere pieno");


    }


    
}
