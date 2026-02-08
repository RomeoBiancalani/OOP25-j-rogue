package it.unibo.rogue.model;

import org.junit.jupiter.api.Test;

import it.unibo.rogue.entity.items.api.Item;
import it.unibo.rogue.entity.items.api.ItemFactory;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.Gold;
import it.unibo.rogue.entity.items.impl.HealthPotion;
import it.unibo.rogue.entity.items.impl.ItemFactoryImpl;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test for ItemFactory.
 */
class ItemFactoryTest {
    private static final int EXPECTED_DMG = 6;
    private static final int LEVEL = 10;
    private static final int ARMOR_COUNT = 20;
    private ItemFactory factory;

    /**
     * Works like a costructor.
     */
    @BeforeEach
    void prepare() {
        this.factory = new ItemFactoryImpl();
    }

    @Test
    void testCreateStartingWeapon() {
        final Item item = factory.createStartingWeapon();
        assertNotNull(item, "L'arma iniziale deve esistere");

        assertTrue(item instanceof MeleeWeapon, "L'oggetto iniziale deve essere un'arma");

        final MeleeWeapon weapon = (MeleeWeapon) item;
        assertEquals("Spada iniziale", weapon.getName(), "Il nome della prima arma dovrebbe essere 'Spada iniziale'");
        assertEquals(EXPECTED_DMG, weapon.getDamage(), "Il danno base della spada iniziale dovrebbe essere 6");
    }

    @Test
    void testRandomArmorStats() {
        for (int i = 0; i < ARMOR_COUNT; i++) {
            final Item item = factory.createRandomArmor(LEVEL);

            assertTrue(item instanceof Armor, "Deve essere un'armatura");
            final Armor armor = (Armor) item;
            assertTrue(armor.getProtection() > 0, "L'armatura deve di base");

        }
    }

    @Test
    void testScalingWithLevel() {
        final int damageLevel1 = getDamageSample(1);
        final int damageLevel10 = getDamageSample(LEVEL);

        if (damageLevel1 > 0 && damageLevel10 > 0) {
            assertTrue(damageLevel10 > damageLevel1, "Un'arma di livello 10 (" + damageLevel10
                    + ") deve essere più forte di un'arma di livello 1 (" + damageLevel1 + ")");
        }
    }

    /**
     * Helper method to get the damage of an item if it is a MeleeWeapon.
     * 
     * @param level the level to test weapons of different levels.
     * 
     * @return the damage of the weapon at the specific level.
     */
    private int getDamageSample(final int level) {
        for (int i = 0; i < 1000; i++) {
            final Optional<Item> result = factory.createRandomItem(level);
            if (result.isPresent() && result.get() instanceof MeleeWeapon) {
                return ((MeleeWeapon) result.get()).getDamage();
            }
        }
        return -1;
    }

    @Test
    void testRandomGenerationConsistency() {
        for (int i = 0; i < 1000; i++) {
            final Optional<Item> result = factory.createRandomItem(LEVEL);

            assertNotNull(result, "Il metodo non deve essere mai null");

            if (result.isPresent()) {
                final Item item = result.get();

                final boolean isValid = item instanceof Armor || item instanceof MeleeWeapon || item instanceof Gold
                        || item instanceof HealthPotion;

                assertTrue(isValid,
                        "L'item generato (" + item.getClass().getSimpleName() + ") non è un tipo valido conosciuto");
            }
        }
    }

}
