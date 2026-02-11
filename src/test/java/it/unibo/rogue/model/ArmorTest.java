package it.unibo.rogue.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.entities.impl.player.PlayerImpl;
import it.unibo.rogue.entity.items.impl.Armor;

/**
 * Test for the Armor class.
 */
class ArmorTest {
    private static final int EXPECTED_DEF = 10;

    @Test
    void testArmorCreation() {

        final String expectedName = "Armatura di acciaio";
        final Armor armor = new Armor(expectedName, EXPECTED_DEF);

        assertEquals(EXPECTED_DEF, armor.getBonus(), "La difesa dovrebbe essere 10");
        assertEquals(expectedName, armor.getName(), "il nome non corrisponde");
    }

    @Test
    void testDescriptionFormat() {
        final Armor armor = new Armor("Armatura", EXPECTED_DEF);
        final String desc = armor.getDescription();

        assertTrue(desc.contains("Armatura"), "La descrizione deve contenere il nome");
        assertTrue(desc.contains("10"), "La descrizione deve contenere il valore di difesa");
    }

    @Test
    void testIllegalCreation() {
        // Test difesa negativa.
        assertThrows(IllegalArgumentException.class, () -> {
            new Armor("Armatura test", -EXPECTED_DEF);
        }, "Non si deve poter creare un'armatura con difesa negativa");

        // test nome null.
        assertThrows(IllegalArgumentException.class, () -> {
            new Armor(null, EXPECTED_DEF);
        }, "Non si deve poter creare un'armatura con nome null");

        // test armatura senza nome.
        assertThrows(IllegalArgumentException.class, () -> {
            new Armor("", EXPECTED_DEF);
        }, "Non si deve poter creare un'armatura senza nome");
    }

    @Test
    void testEquip() {
        final Armor armor = new Armor("Armor", EXPECTED_DEF);
        final Player player = new PlayerImpl(100, 1, 0, new Position(0, 0));

        armor.equip(player);
        assertEquals(EXPECTED_DEF, player.getArmorClass(), "La difesa del player deve aumentare");
    }

}
