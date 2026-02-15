package it.unibo.jrogue.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.entities.impl.player.PlayerImpl;
import it.unibo.jrogue.entity.items.impl.Armor;

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

        assertTrue(desc.contains("Armatura"), "The description must contain the name of the armor");
        assertTrue(desc.contains(String.valueOf(EXPECTED_DEF)), "The description must contain the protection of the armor");
    }

    @Test
    void testIllegalCreation() {
        // Test difesa negativa.
        assertThrows(IllegalArgumentException.class, () -> {
            new Armor("Armor test", -EXPECTED_DEF);
        }, "The armor's protection must be positive");

        // test null name.
        assertThrows(IllegalArgumentException.class, () -> {
            new Armor(null, EXPECTED_DEF);
        }, "The armor's name can not be null");

        // test armatura senza nome.
        assertThrows(IllegalArgumentException.class, () -> {
            new Armor("", EXPECTED_DEF);
        }, "The armor must have a name");
    }

    @Test
    void testEquip() {
        final Armor armor = new Armor("Armor", EXPECTED_DEF);
        final Player player = new PlayerImpl(100, 1, 0, new Position(0, 0));

        armor.equip(player);
        assertEquals(EXPECTED_DEF, player.getArmorClass(), "The defence of the player must increase");
    }

}
