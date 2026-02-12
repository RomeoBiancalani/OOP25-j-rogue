package it.unibo.jrogue.entity.items.impl;

import java.util.Objects;

import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Equipment;

/**
 * Implementation of a melee weapon.
 */
public class MeleeWeapon implements Equipment {

    private final String name;
    private final int damage;

    /**
     * Constructor for MeleeWeapon.
     * 
     * @param name   the name of the weapon.
     * 
     * @param damage the damage of the weapon.
     */
    public MeleeWeapon(final String name, final int damage) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("L'arma deve avere un nome valido");
        }

        if (damage <= 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo o zero");
        }

        this.name = name;
        this.damage = damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return name + " (Danno " + damage + ")";
    }

    /**
     * Provides the name of the weapon.
     * 
     * @return the name of the weapon.
     */
    public String getName() {
        return name;
    }

    /**
     * Provides the damage value of the weapon.
     * 
     * @return the damage value of the weapon.
     */
    @Override
    public int getBonus() {
        return damage;
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
     * {@inheritDoc}
     * 
     * @throws NullPointerException if player is null.
     */
    @Override
    public void unequip(final Player player) {
        Objects.requireNonNull(player).remove(this);
    }
}
