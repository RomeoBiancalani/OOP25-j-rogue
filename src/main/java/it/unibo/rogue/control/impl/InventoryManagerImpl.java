package it.unibo.rogue.control.impl;

import java.util.Optional;

import it.unibo.rogue.control.api.InventoryManager;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.entities.impl.player.PlayerImpl;
import it.unibo.rogue.entity.items.api.Consumable;
import it.unibo.rogue.entity.items.api.Equipment;
import it.unibo.rogue.entity.items.api.Item;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;
import it.unibo.rogue.entity.items.impl.Ring;

/**
 * Class that implements the InventoryManager interface.
 */
public class InventoryManagerImpl implements InventoryManager {

    private static final int DAMAGE_SWORD = 5;
    private static final int PROTECTION = 2;
    private static final int DAMAGE_AXE = 10;
    private static final int HEALING_FACTOR = 10;
    private final Player player;

    /**
     * Costructor.
     *
     * @param size the size of the inventory.
     */
    public InventoryManagerImpl() {
        this.player = new PlayerImpl(100, 1, 0, new Position(0, 0));
        initTestItems();
    }

    /**
     * Private method that puts some items in the inventory
     * to be able to test the GUI inventory.
     */
    private void initTestItems() {
        player.getInventory().addItem(new MeleeWeapon("Spada", DAMAGE_SWORD));
        player.getInventory().addItem(new Armor("Armatura", PROTECTION));
        player.getInventory().addItem(new MeleeWeapon("Ascia", DAMAGE_AXE));
        player.getInventory().addItem(new Ring("anello misterioso", HEALING_FACTOR));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final int index) {
        final Optional<Item> result = player.getInventory().getItem(index);

        if (result.isPresent()) {
            final Item item = result.get();
            if (item instanceof Equipment) {
                ((Equipment) item).equip(player);
                System.out.println("Equipaggiato");
            }
            if (item instanceof Consumable) {
                ((Consumable) item).consume(player);
                System.out.println("consumato");
                player.remove((Equipment) item);
            }
        } else {
            System.out.println("Slot vuoto");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return player.getInventory().getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Item> getItemAt(final int index) {
        return player.getInventory().getItem(index);
    }
}
