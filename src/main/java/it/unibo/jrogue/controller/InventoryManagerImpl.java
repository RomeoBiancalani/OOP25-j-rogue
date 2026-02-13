package it.unibo.jrogue.controller;

import java.util.Optional;

import it.unibo.jrogue.controller.api.InventoryManager;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Consumable;
import it.unibo.jrogue.entity.items.api.Equipment;
import it.unibo.jrogue.entity.items.api.Item;

/**
 * Class that implements the InventoryManager interface.
 */
public class InventoryManagerImpl implements InventoryManager {
    private final Player player;

    /**
     * Costructor.
     * 
     * @param the player.
     */
    public InventoryManagerImpl(final Player player) {
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final int index) {
        final Optional<Item> result = player.getInventory().getItem(index);

        if (result.isPresent()) {
            final Item item = result.get();
            if (item instanceof Equipment equipment) {
                if (player.isEquipped(equipment)) {
                    equipment.unequip(player);
                    System.out.println("Disequipaggiato");
                } else {
                    equipment.equip(player);
                    System.out.println("Equipaggiato");
                }
            }
            if (item instanceof Consumable) {
                ((Consumable) item).consume(player);
                System.out.println("consumato");
                player.getInventory().removeItem(index);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEquipped(final int index) {
        final Optional<Item> itemOpt = player.getInventory().getItem(index);
        if (itemOpt.isPresent()) {
            if (itemOpt.get() instanceof Equipment equipment) {
                return player.isEquipped(equipment);
            }
        }
        return false;
    }
}
