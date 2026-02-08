package it.unibo.rogue.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.rogue.entity.items.impl.MeleeWeapon;

/**
 * Test for the MeleeWeapon class.
 */
class WeaponTest {
    private static final int EXPECTED_ATT = 10;

    @Test
    void testMeleeWeaponCreation() {

        final String expectedName = "spada";
        final MeleeWeapon weapon = new MeleeWeapon(expectedName, EXPECTED_ATT);

        assertEquals(EXPECTED_ATT, weapon.getDamage(), "L'attacco dovrebbe essere 10");
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
        // test attacco negativo.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon("spadona", -1);
        }, "Non di deve poter creare un arma con attacco negativo");

        // test nome null.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon(null, EXPECTED_ATT);
        }, "Non si deve poter creare un arma con nome null");

        // test arma senza nome.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon("", EXPECTED_ATT);
        }, "Non si deve poter creare un arma senza nome");

    }
}
