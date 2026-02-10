package it.unibo.rogue.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.entities.impl.player.PlayerImpl;
import it.unibo.rogue.entity.items.api.Equipment;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;

/**
 * Test player feature.
 */
class PlayerTest {

    private static final Position START_POS = new Position(0, 0);
    private static final int START_HP = 15;
    private static final int BASE_AC = 3;
    private static final int START_LEVEL = 1;

    private Player player;

    /**
     * Set up a player with:
     *  - Level: 1
     *  - HP: 15
     *  - AC: 3
     */
    @BeforeEach
    void setUp() {
        player = new PlayerImpl(START_HP, START_LEVEL, BASE_AC, START_POS);
    }

    /**
     * Test player Constructor.
     */
    @Test
    void testPlayerConstruction() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerImpl(-1, START_LEVEL, BASE_AC, START_POS));
        assertThrows(IllegalArgumentException.class, () -> new PlayerImpl(START_HP, START_LEVEL, BASE_AC, null));
    }

    @Test
    void testMovement() {
        assertEquals(new Position(0, 0), player.getPosition());
        player.doMove(Move.DOWN);
        assertEquals(new Position(0, 1), player.getPosition());

    }

    /**
     * Check life point and damage management.
     */
    @Test
    void testHealthandDamage() {
        // Check player start hp and max HP.
        assertEquals(START_HP, player.getMaxLifePoint());
        assertEquals(START_HP, player.getLifePoint());
        assertTrue(player.isAlive());
        
        // Damage must be non negative.
        assertThrows(IllegalArgumentException.class, () -> player.damage(-1));

        // Test player damage
        int damage = 5;
        player.damage(damage);
        assertEquals(START_HP - damage, player.getLifePoint());
        assertTrue(player.isAlive());

        // Heal amount must be non negative.
        assertThrows(IllegalArgumentException.class, () -> player.heal(-1));

        // Test player heal
        int heal = 1;
        player.heal(heal);
        final int hp = damage - heal;
        assertEquals(player.getLifePoint(), hp);
        assertTrue(player.isAlive());
        heal = 100;
        player.heal(heal);
        assertEquals(player.getLifePoint(), START_HP);
        assertTrue(player.isAlive());

        // Test player death
        damage = 100;
        player.damage(damage);
        assertFalse(player.isAlive());

        // Can't heal/damage a dead player
        assertThrows(IllegalStateException.class, () -> player.heal(1));
        assertThrows(IllegalStateException.class, () -> player.damage(1));
    }

    /**
     * Test experience overflow and levelUp.
     */
    @Test
    void testLevelUp() {
        // XP collected must be positive
        assertThrows(IllegalArgumentException.class, () -> player.collectXP(-1));

        assertEquals(START_LEVEL, player.getLevel());

        // XP treshold to levelup: 20.
        // Expected results: 50/20 = +2 levels (10 xp left).
        //                   + (3 * 2) maxLP (3 per level).
        player.collectXP(50);
        assertEquals(START_LEVEL + 2, player.getLevel());
        assertEquals(START_HP + (3 * 2), player.getMaxLifePoint());

        // 10 (xp left) + 10 = new level.
        player.collectXP(10);
        assertEquals(START_LEVEL + 3, player.getLevel());
        assertEquals(START_HP + (3 * 3), player.getMaxLifePoint());

    }

    /**
     * Check correct integration of equipment and statistic bonus.
     */
    @Test
    void testArmorEquipmentLogic() {
        // Armor to equip must be not null.
        assertThrows(NullPointerException.class, () -> player.equip(null));
        // Armor to remove must be not null.
        assertThrows(NullPointerException.class, () -> player.remove(null));

        // Equip a leather armor and test his bonus.
        final Equipment leatherArmor = new Armor("Leather Armor", 2);
        player.equip(leatherArmor);
        assertEquals(BASE_AC + 2, player.getArmorClass());

        // Equip a new armor and test his bonus.
        final Equipment plateArmor = new Armor("Plate Armor", 5);
        player.equip(plateArmor);
        assertEquals(BASE_AC + 5, player.getArmorClass());

        // Can't remove an armor that is not the equipped armor
        assertThrows(IllegalArgumentException.class, () -> player.remove(leatherArmor));
        // Unequip armor
        player.remove(plateArmor);
        assertEquals(BASE_AC, player.getArmorClass());
        // Can't remove an armor if there isn't an equipped armor.
        assertThrows(IllegalArgumentException.class, () -> player.remove(plateArmor));
    }

    /**
     * Check correct hitBonus range and correct integration of weapon equipment
     */
    @Test
    void testHitBonusRangeAndWeapon() {
        // weapon to equip must be not null.
        assertThrows(NullPointerException.class, () -> player.equip(null));
        // weapon to remove must be not null.
        assertThrows(NullPointerException.class, () -> player.remove(null));

        // Player max hit bonus = 3 (base hit bonus).
        final int bound = 50;
        for (int i = 0; i < bound; i++) {
            final int bonus = player.getHitBonus();
            assertTrue(bonus >= 1 && bonus <= 3);
        }

        final MeleeWeapon sword = new MeleeWeapon("sword", 2);
        player.equip(sword);

        // Player hit bonus = 3 (base hit bonus) + 2 (sword bonus).
        for (int i = 0; i < bound; i++) {
            final int bonus = player.getHitBonus();
            assertTrue(bonus >= 1 && bonus <= sword.getBonus() + 3);
        }

        final int axeDamage = 5;
        final  MeleeWeapon axe = new MeleeWeapon("axe", axeDamage);
        // Can't remove a weapon that is not the equipped weapon
        assertThrows(IllegalArgumentException.class, () -> player.remove(axe));

        player.equip(sword);
        // Player hit bonus = 3 (base hit bonus) + 5 (axe bonus).
        for (int i = 0; i < 20; i++) {
            final int bonus = player.getHitBonus();
            assertTrue(bonus >= 1 && bonus <= axe.getBonus() + 3);
        }
    }

}
