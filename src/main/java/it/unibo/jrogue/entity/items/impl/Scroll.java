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

    @Override
    public boolean consume(final Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            final Optional<Item> invItem = inventory.getItem(i);
            if (invItem.isPresent()) {
                final Item item = invItem.get();
                if (item instanceof Ring ring) {
                    if(ring.getIsIdentified()){
                        continue;
                    }
                    ring.identify();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Use it when you have a ring and something may happen";
    }
}
