package it.unibo.jrogue.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.impl.enemies.factory.EnemyFactory;
import it.unibo.jrogue.entity.entities.impl.enemies.factory.EnemyFactoryImpl;

/**
 * Test class for EnemyFactory class
 */
public class EnemyFactoryTest {

    private static final Position DEFAULT_POS = new Position(0, 0);
    private EnemyFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new EnemyFactoryImpl();
    }

    @Test
    void testPecificEnemiesCreation() {
        assertNotNull(factory.createBat(DEFAULT_POS, 1), "Bat should't be null");
        assertNotNull(factory.createHobGoblin(DEFAULT_POS, 1), "HobGoblin should't be null");
        assertNotNull(factory.createDragon(DEFAULT_POS, 1), "Dragon should't be null");
    }

    @Test
    void testStatsScaling() {
        final int level1 = 1;
        final int level10 = 10;

        final Enemy weakBat = factory.createBat(DEFAULT_POS, level1);
        final Enemy strongBat = factory.createBat(DEFAULT_POS, level10);
        assertTrue(strongBat.getArmorClass() >= weakBat.getArmorClass(),
                    "Higher level bat should have more or equals AC");

        final Enemy weakGoblin = factory.createHobGoblin(DEFAULT_POS, level1);
        final Enemy strongGoblin = factory.createHobGoblin(DEFAULT_POS, level10);
        assertTrue(strongGoblin.getArmorClass() >= weakGoblin.getArmorClass(),
                    "Higher level goblin should have more or equals AC");

        final Enemy weakDragon = factory.createDragon(DEFAULT_POS, level1);
        final Enemy strongDragon = factory.createDragon(DEFAULT_POS, level10);
        assertTrue(strongDragon.getArmorClass() >= weakDragon.getArmorClass(),
                    "Higher level dragon should have more or equals AC");
    }

    @Test
    void testLevelRequirements() {
        for(int i = 0; i < 100; i++) {
            final Enemy enemy = factory.createRandomEnemy(DEFAULT_POS, 1);
            // Only bats should spawn at level 1
            assertEquals("Bat", enemy.getClass().getSimpleName(),
                        "Only bat should spawn at level 1");
        }

        boolean batFound = false;
        boolean goblinFound = false;
        boolean dragonFound = false;
        for (int i = 0; i < 200; i++) {
            final Enemy enemy = factory.createRandomEnemy(DEFAULT_POS, 2);
            if (enemy.getClass().getSimpleName().equals("Bat")) {
                batFound = true;
            } else if (enemy.getClass().getSimpleName().equals("HobGoblin")) {
                goblinFound = true;
            } else if (enemy.getClass().getSimpleName().equals("Dragon")) {
                dragonFound = true;
            }
        }
        assertTrue(batFound, "Bat should be able to spawn at level 3");
        assertTrue(goblinFound, "HobGoblin should be able to spawn at level 3");
        assertFalse(dragonFound, "Dragon shouldn't be able to spawn at level 3");

        boolean dFound = false;
        for (int i = 0; i < 200; i++) {
            final Enemy enemy = factory.createRandomEnemy(DEFAULT_POS, 4);
            if (enemy.getClass().getSimpleName().equals("Dragon")) {
                dFound = true;
                break;
            }
        }
        assertTrue(dFound, "Dragon should be able to spawn at level 4");
    }
}
