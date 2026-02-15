package it.unibo.jrogue.entity.items.impl;

import it.unibo.jrogue.entity.items.api.Item;

/**
 * Class that implements the Amulet of Yendor.
 */
public class Amulet implements Item {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "You have found the amulet of Yendor!!!";
    }

}
