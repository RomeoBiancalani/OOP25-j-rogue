package it.unibo.rogue.entity.items.impl;

import java.util.Objects;

import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.items.api.Equipment;

/**
 * Implementation of an armor item.
 */
public final class Armor implements Equipment {
    private final String name;
    private final int protection;

    /**
     * The constructor of armor.
     *
     * @param name       the name of the armor.
     *
     * @param protection the protection of the armor.
     */
    public Armor(final String name, final int protection) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("L'armatura deve avere un nome valido");
        }

        if (protection < 0) {
            throw new IllegalArgumentException("La difesa non può essere negativa");
        }

        this.name = name;
        this.protection = protection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return name + " (Defence: " + protection + ")";
    }

    /**
     * Provides the name of the armor.
     * 
     * @return the name of the armor.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Provides the protection value of the armor.
     *
     * @return the protection value.
     */
    @Override
    public int getBonus() {
        return this.protection;
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
}
