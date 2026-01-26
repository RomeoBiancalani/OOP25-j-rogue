package it.unibo.rogue.entity.items;

public interface Inventory {
    boolean isFull();
    Item getItem();
    boolean addItem(Item item);
}
