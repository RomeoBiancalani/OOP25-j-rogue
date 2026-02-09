package it.unibo.jrogue.entity.items.impl;

import it.unibo.jrogue.entity.items.api.Consumable;

/**
 * A scroll that can be consumed for various effects.
 */
public final class Scrolls implements Consumable {

    private final String name;

    /**
     * Creates a new scroll with the given name.
     *
     * @param name the scroll name
     */
    Scrolls(final String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return name + " ";
    }

    @Override
    public void consume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consume'");
    }
}
