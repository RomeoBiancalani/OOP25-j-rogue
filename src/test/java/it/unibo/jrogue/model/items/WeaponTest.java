package it.unibo.jrogue.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.entities.impl.player.PlayerImpl;
import it.unibo.jrogue.entity.items.impl.MeleeWeapon;

/**
 * Test for the MeleeWeapon class.
 */
class WeaponTest {
    private static final int EXPECTED_ATT = 10;
    private static final int PLAYER_HEALTH = 100;
    private static final int PLAYER_LEVEL = 1;
    private static final int PLAYER_ARMOR = 0;
    private static final Position PLAYER_POS = new Position(0, 0);
    private Player player;

    @BeforeEach
    void setPlayer() {
        this.player = new PlayerImpl(PLAYER_HEALTH, PLAYER_LEVEL, PLAYER_ARMOR, PLAYER_POS);
    }

    @Test
    void testMeleeWeaponCreation() {

        final String expectedName = "spada";
        final MeleeWeapon weapon = new MeleeWeapon(expectedName, EXPECTED_ATT);

        assertEquals(EXPECTED_ATT, weapon.getBonus(), "L'attacco dovrebbe essere 10");
        assertEquals(expectedName, weapon.getName(), "Il nome dovrebbe essere 'spada' ");
    }

    @Test
    void testDescriptionFormat() {
        final MeleeWeapon weapon = new MeleeWeapon("ascia", EXPECTED_ATT);
        final String desc = weapon.getDescription();

        assertTrue(desc.contains("ascia"), "La descrizione dovrebbe contenere il nome");
        assertTrue(desc.contains("10"), "La descrizione dovrebbe contenere l'attacco dell'arma");
    }

    @Test
    void testIllegalCreation() {
        // test negative attack.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon("spadona", -1);
        }, "Non si deve poter creare un arma con attacco negativo");

        // test null name.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon(null, EXPECTED_ATT);
        }, "Non si deve poter creare un arma con nome null");

        // test weapon without name.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon("", EXPECTED_ATT);
        }, "Non si deve poter creare un arma senza nome");
    }

    //In this test we can not determine with accuracy the damage of the player
    //with an equipped weapon because the base damage of the Player is random
    //so we simply test if the weapon is equipped.
    @Test
    void testEquip() {
        final MeleeWeapon weapon = new MeleeWeapon("spada", EXPECTED_ATT);

        weapon.equip(player);

        final int randomDamage = player.getAttack();

        assertTrue(randomDamage > 0, "il player armato deve fare danni positivi");

    }
}
