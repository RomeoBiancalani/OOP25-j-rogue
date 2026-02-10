package it.unibo.rogue.entity.items.impl;

import java.util.Objects;

import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.items.api.Equipment;

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
            throw new IllegalArgumentException("L'anello deve avere un nome valido");
        }
        if (healingFactor < 0) {
            throw new IllegalArgumentException("L'anello non può curare in negativo");
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
            return "Anello misterioso";
        }
        return name + " (Guarigione: + " + healingFactor + ")";
    }

    /**
     * Provides the name of the ring.
     * 
     * @return the name of the ring.
     */
    public String getName() {
        return isIdentified ? this.name : "Anello misterioso";
    }

    /**
     * Provides the healing factor of the ring.
     * 
     * @return the healing factor of the ring.
     */
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
     */
    @Override
    public void equip(final Player player) {
        if (player != null) {
            player.equip(this);
        }
    }

    /**
     * {@inhheritDoc}
     * 
     * @throws NullPointerException if player is null
     */
    @Override
    public void unequip(Player player) {
        Objects.requireNonNull(player).remove(this);
    }
}
