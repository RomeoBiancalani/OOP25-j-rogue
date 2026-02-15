package it.unibo.jrogue.model.items;

import org.junit.jupiter.api.Test;

import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.items.api.ItemFactory;
import it.unibo.jrogue.entity.items.impl.Armor;
import it.unibo.jrogue.entity.items.impl.Food;
import it.unibo.jrogue.entity.items.impl.Gold;
import it.unibo.jrogue.entity.items.impl.HealthPotion;
import it.unibo.jrogue.entity.items.impl.ItemFactoryImpl;
import it.unibo.jrogue.entity.items.impl.MeleeWeapon;
import it.unibo.jrogue.entity.items.impl.Ring;
import it.unibo.jrogue.entity.items.impl.Scroll;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test for ItemFactory.
 */
class ItemFactoryTest {
    private static final int LEVEL10 = 10;
    private static final int LEVEL1 = 1;
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
    void testRandomArmorStats() {
        for (int i = 0; i < ARMOR_COUNT; i++) {
            final Item item = factory.createRandomArmor(LEVEL1);

            assertTrue(item instanceof Armor, "Deve essere un'armatura");
            final Armor armor = (Armor) item;
            assertTrue(armor.getBonus() > 0, "L'armatura deve di base");

        }
    }

    @Test
    void testScalingWithLevel() {
        final Item itemLevel1 = factory.createWeapon("spada", LEVEL1);
        final Item itemLevel10 = factory.createWeapon("spada", LEVEL10);

        assertTrue(itemLevel1 instanceof MeleeWeapon);
        assertTrue(itemLevel10 instanceof MeleeWeapon);

        final int dmg1 = ((MeleeWeapon) itemLevel1).getBonus();
        final int dmg10 = ((MeleeWeapon) itemLevel10).getBonus();

        assertTrue(dmg10 > dmg1,
                "una spada di livello 10 (" + dmg10 + " ) deve essere più forte di una di livello 1 (" + dmg1 + ")");
    }

    @Test
    void testRandomGenerationConsistency() {
        for (int i = 0; i < 1000; i++) {
            final Optional<Item> result = factory.createRandomItem(LEVEL1);

            assertNotNull(result, "Il metodo non deve essere mai null");

            if (result.isPresent()) {
                final Item item = result.get();

                final boolean isValid = item instanceof Armor || item instanceof MeleeWeapon || item instanceof Gold
                        || item instanceof HealthPotion || item instanceof Ring || item instanceof Food
                        || item instanceof Scroll;

                assertTrue(isValid,
                        "L'item generato (" + item.getClass().getSimpleName() + ") non è un tipo valido conosciuto");
            }
        }
    }

}
