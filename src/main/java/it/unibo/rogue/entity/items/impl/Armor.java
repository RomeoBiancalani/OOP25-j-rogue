package it.unibo.rogue.entity.items.impl;

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
     * @param name the name of the armor.
     *
     * @param protection the protection of the armor.
     */
    public Armor(final String name, final int protection) {
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
     * Provides with the protection stat of the armor.
     *
     * @return the protection value.
     */
    public int getProtection() {
        return protection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void equip(final Player player) {
        if (player != null) {
            player.equipArmor(this);
        }
    }
}
