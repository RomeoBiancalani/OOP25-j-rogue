package it.unibo.jrogue.entity.items.impl;

import java.util.Objects;

import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Equipment;

/**
 * Implementation of the Ring item.
 */
public class Ring implements Equipment {

    private final String name;
    private final int healingFactor;
    private boolean isIdentified;

    /**
     * Constructor for the Ring class.
     * 
     * @param name          of the ring.
     * 
     * @param healingFactor of the ring.
     */
    public Ring(final String name, final int healingFactor) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The ring needs a valid name");
        }
        if (healingFactor < 0) {
            throw new IllegalArgumentException("The healing factor of the ring can not be negative");
        }
        this.name = name;
        this.healingFactor = healingFactor;
        this.isIdentified = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        if (!isIdentified) {
            return "Mysterious ring";
        }
        return name + " (Healing: + " + healingFactor + ")";
    }

    /**
     * Provides the name of the ring.
     * 
     * @return the name of the ring.
     */
    public String getName() {
        return isIdentified ? this.name : "Mysterious ring";
    }

    /**
     * Provides the healing factor of the ring.
     * 
     * @return the healing factor of the ring.
     */
    @Override
    public int getBonus() {
        return this.healingFactor;
    }

    /**
     * Method that makes the ring description readable.
     */
    public void identify() {
        this.isIdentified = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if player is null.
     */
    @Override
    public void equip(final Player player) {
        Objects.requireNonNull(player).equip(this);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if player is null.
     */
    @Override
    public void unequip(final Player player) {
        Objects.requireNonNull(player).remove(this);
    }

    /**
     * Getter for isIdentified.
     * 
     * @return true if the ring is identified.
     */
    public boolean isIdentified() {
        return this.isIdentified;
    }
}
