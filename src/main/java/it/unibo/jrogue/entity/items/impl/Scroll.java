package it.unibo.jrogue.entity.items.impl;

import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Consumable;
import it.unibo.jrogue.entity.items.api.Inventory;
import it.unibo.jrogue.entity.items.api.Item;

import java.util.Optional;

/**
 * Class that manages the scroll item. 
 */
public class Scroll implements Consumable {
    private int consumableAmount;

    @Override
    public void consume(final Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            final Optional<Item> invItem = inventory.getItem(i);
            if (invItem.isPresent()) {
                final Item item = invItem.get();
                if (item instanceof Ring ring) {
                    ring.identify();
                    consumableAmount--;
                    return;
                }
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "It may help you discover the first ring effect";
    }

    /**
     * Getter for item amount.
     *
     * @return consumableAmount.
     */

    public int getConsumableAmount() {
        return consumableAmount;
    }
}
