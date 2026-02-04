package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.items.api.Equipment;

/**
 * Implementation of a melee weapon.
 */
public class MeleeWeapon implements Equipment {

    private final String name;
    private final int damage;

    /**
     * Constructor for MeleeWeapon.
     * 
     * @param name the name of the weapon.
     * 
     * @param damage the damage of the weapon.
     */
    public MeleeWeapon(final String name, final int damage) {
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
     * Provides the damage value of the weapon.
     * 
     * @return the damage value of the weapon.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void equip(final Player player) {
        if (player != null) {
            player.equipWeapon(this);
        } 
    }
}
