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

        final String expectedName = "sword";
        final MeleeWeapon weapon = new MeleeWeapon(expectedName, EXPECTED_ATT);

        assertEquals(EXPECTED_ATT, weapon.getBonus(), "The attack should be 10");
        assertEquals(expectedName, weapon.getName(), "The name of the sword should be :'sword' ");
    }

    @Test
    void testDescriptionFormat() {
        final MeleeWeapon weapon = new MeleeWeapon("shovel", EXPECTED_ATT);
        final String desc = weapon.getDescription();

        assertTrue(desc.contains("shovel"), "The description should contain the name of the weapon");
        assertTrue(desc.contains("10"), "The description should contain the attack of the weapon");
    }

    @Test
    void testIllegalCreation() {
        // test negative attack.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon("spear", -1);
        }, "The weapon's attack must be positive");

        // test null name.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon(null, EXPECTED_ATT);
        }, "The weapon's name can not be null");

        // test weapon without name.
        assertThrows(IllegalArgumentException.class, () -> {
            new MeleeWeapon("", EXPECTED_ATT);
        }, "The weapon must have a name");
    }

    //In this test we can not determine with precision the damage of the player
    //with an equipped weapon because the damage of the player is Random.
    @Test
    void testEquip() {
        final MeleeWeapon weapon = new MeleeWeapon("sword", EXPECTED_ATT);

        weapon.equip(player);

        final int randomDamage = player.getAttack();

        assertTrue(randomDamage > 0, "The player with an equipped weapon must have positive damage");

    }
}
