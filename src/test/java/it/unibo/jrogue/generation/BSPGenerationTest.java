package it.unibo.jrogue.generation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.generation.api.GenerationConfig;
import it.unibo.jrogue.controller.generation.api.LevelGenerator;
import it.unibo.jrogue.controller.generation.impl.BSPLevelGenerator;
import it.unibo.jrogue.entity.world.api.GameMap;
import it.unibo.jrogue.entity.world.api.Level;

/**
 * Tests for the level generator.
 * To run: ./gradlew clean test --tests
 * "it.unibo.jrogue.generation.BSPLevelGeneratorTest" --console=verbose.
 */
class BSPLevelGeneratorTest {
    private static final int MAP_WIDTH = 80;
    private static final int MAP_HEIGHT = 45;
    private static final int LEVEL_NUMBER = 1;
    private static final long TEST_SEED = 1234L;

    private LevelGenerator generator;
    private GenerationConfig config;

    @BeforeEach
    void start() {
        // Starting setup
        generator = new BSPLevelGenerator();
        config = GenerationConfig.withDefaults(MAP_WIDTH, MAP_HEIGHT, LEVEL_NUMBER, TEST_SEED);
    }

    @Test
    void testGenerateReturnsNonNullLevel() {
        // Make sure that the level and the map are not null
        final Level level = generator.generate(config);
        assertNotNull(level);
        assertNotNull(level.getMap());
    }

    @Test
    void testMapHasCorrectDimensions() {
        // Let's make sure that the generated map follows parameters
        final Level level = generator.generate(config);
        final GameMap map = level.getMap();
        assertEquals(MAP_WIDTH, map.getWidth());
        assertEquals(MAP_HEIGHT, map.getHeight());
    }

    @Test
    void testMapHasAtLeastOneRoom() {
        // Let's make sure the level has at least one room
        final Level level = generator.generate(config);
        final GameMap map = level.getMap();
        assertFalse(map.getRooms().isEmpty(), "La mappa deve avere almeno una stanza");
    }

    @Test
    void testStartingPositionIsWalkable() {
        // The position where the player spawns needs to be walkable
        final Level level = generator.generate(config);
        final GameMap map = level.getMap();
        final Position start = map.getStartingPosition();

        assertNotNull(start, "La posizione iniziale non deve essere null");
        assertTrue(map.isWalkable(start), "La posizione iniziale deve essere calpestabile");
    }

    @Test
    void testDeterministicGeneration() {
        // Same seed, same map
        final Level level1 = generator.generate(config);
        final Level level2 = generator.generate(config);

        final GameMap map1 = level1.getMap();
        final GameMap map2 = level2.getMap();
        // Checks rooms (size)
        assertEquals(map1.getRooms().size(), map2.getRooms().size(),
                "Stesse stanze con stesso seed");
        // Checks startingPos
        assertEquals(map1.getStartingPosition(), map2.getStartingPosition(),
                "Stessa posizione iniziale con stesso seed");
        // Checks enemies
        assertEquals(map1.getEnemies(), map2.getEnemies(),
                "Stessi mostri con stesso seed");
        // Checks Items
        final Map<Position, String> items1 = map1.getItems().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getDescription()));
        final Map<Position, String> items2 = map2.getItems().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getDescription()));
        assertEquals(items1, items2, "Stessi items con stesso seed");
    }

    @Test
    void testDifferentSeedsProduceDifferentMaps() {
        final long otherSeed = 9999L;
        final GenerationConfig otherConfig = GenerationConfig.withDefaults(
                MAP_WIDTH, MAP_HEIGHT, LEVEL_NUMBER, otherSeed);

        final Level level1 = generator.generate(config);
        final Level level2 = generator.generate(otherConfig);

        // Con seed diverse, almeno il numero di stanze o la posizione iniziale
        // dovrebbero differire (non garantito al 100% ma altamente probabile)
        final boolean differentRooms = level1.getMap().getRooms().size() != level2.getMap().getRooms().size();
        final boolean differentStart = !level1.getMap().getStartingPosition()
                .equals(level2.getMap().getStartingPosition());
        final boolean differentEnemies = level1.getMap().getEnemies().size() != level2.getMap().getEnemies().size();

        assertTrue(differentRooms || differentStart || differentEnemies,
                "Seed diversi producono mappe diverse");
    }

    @Test
    void testLevelNumberIsPreserved() {
        final int testLevel = 5;
        final GenerationConfig levelConfig = GenerationConfig.withDefaults(
                MAP_WIDTH, MAP_HEIGHT, testLevel, TEST_SEED);
        final Level level = generator.generate(levelConfig);
        assertEquals(testLevel, level.getLevel());
    }
}
