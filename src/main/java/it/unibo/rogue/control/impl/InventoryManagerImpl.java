package it.unibo.rogue.control.impl;

import java.util.Optional;

import it.unibo.rogue.control.api.InventoryManager;
import it.unibo.rogue.entity.items.api.Inventory;
import it.unibo.rogue.entity.items.api.Item;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;
import it.unibo.rogue.entity.items.impl.SimpleInventory;

/**
 * Class that implements the InventoryManager interface.
 */
public class InventoryManagerImpl implements InventoryManager {

    private static final int DAMAGE_SWORD = 5;
    private static final int PROTECTION = 2;
    private static final int DAMAGE_AXE = 10;
    private final Inventory inventory;

    /**
     * Costructor.
     *
     * @param size the size of the inventory.
     */
    public InventoryManagerImpl(final int size) {
        this.inventory = new SimpleInventory(size);
        initTestItems();
    }

    /**
     * Private method that puts some items in the inventory
     * to be able to test the GUI inventory.
     */
    private void initTestItems() {
        inventory.addItem(new MeleeWeapon("Spada", DAMAGE_SWORD));
        inventory.addItem(new Armor("Armatura", PROTECTION));
        inventory.addItem(new MeleeWeapon("Ascia", DAMAGE_AXE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final int index) {
        final Optional<Item> result = inventory.getItem(index);

        if (result.isPresent()) {
            final Item item = result.get();
            System.out.println("Usato : " + item.getDescription());
        } else {
            System.out.println("Slot vuoto");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return inventory.getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Item> getItemAt(final int index) {
        return inventory.getItem(index);
    }
}
