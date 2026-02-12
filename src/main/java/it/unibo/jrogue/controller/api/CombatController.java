package it.unibo.jrogue.controller.api;

import it.unibo.jrogue.entity.entities.api.Entity;

/**
 * Controller responsible for handling combat interactions between entities.
 */
@FunctionalInterface
public interface CombatController {

    /**
     * Resolve an attack from an attacker to a target.
     * 
     * @param attacker The entity that performs the attack.
     * @param target The enitity being attacked.
     * @return Damage dealt to the target.
     */
    int attack(Entity attacker, Entity target);
}
