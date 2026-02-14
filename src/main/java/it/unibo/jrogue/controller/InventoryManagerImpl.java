package it.unibo.jrogue.controller;

import java.util.Optional;

import it.unibo.jrogue.controller.api.InventoryManager;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Consumable;
import it.unibo.jrogue.entity.items.api.Equipment;
import it.unibo.jrogue.entity.items.api.Item;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Class that implements the InventoryManager interface.
 */
public class InventoryManagerImpl implements InventoryManager {
    private final Player player;

    /**
     * Costructor.
     * 
     * @param player the player.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2", 
        justification = "The manager must hold a reference to the live Player entity to modify its state."
    )
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
                } else {
                    equipment.equip(player);
                }
            }
            if (item instanceof Consumable consumable) {
                final boolean isConsumed = consumable.consume(player);
                if (isConsumed) {
                    player.getInventory().removeItem(index);
                }

            }
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
        if (itemOpt.isPresent() && itemOpt.get() instanceof Equipment equipment) {
            return player.isEquipped(equipment);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dropItem(final int index) {
        final Optional<Item> result = player.getInventory().getItem(index);

        if (result.isPresent()) {
            final Item item = result.get();

            if (item instanceof Equipment && isEquipped(index)) {
                player.remove((Equipment) item);
                // System.out.println("L'oggetto viene disequipaggiato automaticamente");
            }

            player.getInventory().removeItem(index);
            // System.out.println("Oggetto buttato");
        }

    }
}
