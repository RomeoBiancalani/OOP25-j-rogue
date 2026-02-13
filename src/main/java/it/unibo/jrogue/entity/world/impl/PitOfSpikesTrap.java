package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;

public class PitOfSpikesTrap implements Trap {
    private final Position position;
    private final int damage;
    private boolean active;
    private boolean discovered;

    public PitOfSpikesTrap(final Position position) {
        this.position = position;
        this.active = true;
        this.damage = 10;
        this.discovered = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void trigger() {
        this.active = false;
        this.discover();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isDiscovered() {
        return discovered;
    }

    @Override
    public void discover() {
        this.discovered = true;
    }

    public int getDamage() {
        return damage;
    }
}
