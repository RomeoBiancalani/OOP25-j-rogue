package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;
/**
 * Trap which deals major damage.
 * */

public final class PitOfSpikesTrap implements Trap {
    private final Position position;
    private final int damage;
    private boolean active;
    private boolean discovered;
    /**
     * Constructor.
     *
     * @param position which is the position in the map
     * */

    public PitOfSpikesTrap(final Position position) {
        this.position = position;
        this.active = true;
        this.damage = 10;
    }

    @Override
    public void trigger() {
        if (this.active) {
            getDamage();
            this.active = false;
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }


    @Override
    public String getDescription(){
        return "You fell on a trap and lost " + getDamage() + "HP";
    }

    /**
     * getter for trap damage.
     *
     * @return damage of the trap
     * */


    public int getDamage() {
        return damage;
    }
}
