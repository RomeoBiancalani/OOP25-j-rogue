package it.unibo.jrogue.controller;

import java.util.Objects;

import it.unibo.jrogue.controller.api.CombatController;
import it.unibo.jrogue.entity.entities.api.Entity;

/**
 * Standard implementation of the combat system.
 * if RAND + atLVL + atBonus >= defender AC => hit
 */
public class CombatControllerImpl implements CombatController {

    /**
     * {@inheritDoc}
     */
    @Override
    public int attack(final Entity attacker, final Entity target) {
        Objects.requireNonNull(attacker, "Attacker cannot be null");
        Objects.requireNonNull(target, "Attacker cannot be null");
        if (!attacker.isAlive() || !target.isAlive()) {
            throw new IllegalArgumentException("Attcker and target must be alive");
        }
        final int attackValue = attacker.getAttack();
        int damage = 0;
        if (attackValue > target.getArmorClass()) {
            damage = attackValue - target.getArmorClass();
            target.damage(damage);
        }
        return damage;
    }
}
