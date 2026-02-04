package it.unibo.jrogue.items.api;

import java.util.Optional;

public interface Inventory {
    boolean isFull();
    Optional<Item> getItem(int index);
    boolean addItem(Item item);
}
