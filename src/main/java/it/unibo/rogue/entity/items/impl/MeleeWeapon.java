package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.items.api.Equipment;

/**
 * A melee weapon that can be equipped by the player.
 */
public final class MeleeWeapon implements Equipment {

    private final String name;
    private final int damage;

    /**
     * Creates a new melee weapon.
     *
     * @param name the weapon name
     * @param damage the damage value
     */
    public MeleeWeapon(final String name, final int damage) {
        this.name = name;
        this.damage = damage;
    }

    @Override
    public String getDescription() {
        return name + " (Danno " + damage + ")";
    }

    @Override
    public void equip() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equip'");
    }
}
