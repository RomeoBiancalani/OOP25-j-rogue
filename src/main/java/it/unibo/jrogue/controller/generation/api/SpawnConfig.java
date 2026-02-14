package it.unibo.jrogue.controller.generation.api;

/**
 * Configuration for entity and item spawning in dungeon rooms.
 * All probabilities are per-room (0.0 to 1.0 range).
 * Formulas:
 * - HP(L) = HP_base * (1 + alpha*(L-1))
 * - DMG(L) = DMG_base + floor(beta*(L-1))
 * - XP_next(L) = 50*L^2 - 50*L + 100
 * - XP_gain = XP_base * (1 + lambda*(L-1))
 *
 * 
 * @param spikeTrapMinLevel    minimum level for spike traps (3)
 * @param poisonTrapMinLevel   minimum level for poison traps (5)
 * @param teleportTrapMinLevel minimum level for teleport traps (8)
 * @param trapRate             spawn rate for traps when eligible (0.15 default)
 * @param enemySpawnRate       base chance to spawn an enemy in a room (0.30
 *                             default)
 * @param maxEnemiesPerRoom    maximum enemies per room (3 default)
 * @param hpScalingAlpha       HP scaling coefficient (0.4 default)
 * @param damageScalingBeta    damage scaling coefficient (1.2 default)
 * @param xpScalingLambda      XP gain scaling coefficient (0.5 default)
 * @param batBaseWeight        base weight for bat enemy selection (10)
 * @param goblinBaseWeight     base weight for goblin enemy selection (8)
 * @param dragonBaseWeight     base weight for dragon enemy selection (5)
 */
public record SpawnConfig(

        // Traps
        int spikeTrapMinLevel,
        int poisonTrapMinLevel,
        int teleportTrapMinLevel,
        double trapRate,

        // Enemies
        double enemySpawnRate,
        int maxEnemiesPerRoom,
        double hpScalingAlpha,
        double damageScalingBeta,
        double xpScalingLambda,

        // Enemy weights for level-based selection
        int batBaseWeight,
        int goblinBaseWeight,
        int dragonBaseWeight) {

    // Traps
    private static final int DEFAULT_SPIKE_TRAP_MIN_LEVEL = 3;
    private static final int DEFAULT_POISON_TRAP_MIN_LEVEL = 5;
    private static final int DEFAULT_TELEPORT_TRAP_MIN_LEVEL = 8;
    private static final double DEFAULT_TRAP_RATE = 0.15;

    // Enemies
    private static final double DEFAULT_ENEMY_SPAWN_RATE = 0.30;
    private static final int DEFAULT_MAX_ENEMIES_PER_ROOM = 3;
    private static final double DEFAULT_HP_SCALING_ALPHA = 0.4;
    private static final double DEFAULT_DAMAGE_SCALING_BETA = 1.2;
    private static final double DEFAULT_XP_SCALING_LAMBDA = 0.5;

    // Enemy weights
    private static final int DEFAULT_BAT_BASE_WEIGHT = 10;
    private static final int DEFAULT_GOBLIN_BASE_WEIGHT = 8;
    private static final int DEFAULT_DRAGON_BASE_WEIGHT = 5;

    // Debug rates
    private static final double DEBUG_TRAP_RATE = 0.20;
    private static final double DEBUG_ENEMY_SPAWN_RATE = 0.50;
    private static final int DEBUG_ENEMY_WEIGHT = 5;

    // XP formula constants
    private static final int XP_MULTIPLIER = 50;
    private static final int XP_BASE = 100;

    /**
     * Creates a SpawnConfig with default values matching design specs.
     *
     * @return default spawn configuration
     */
    public static SpawnConfig defaults() {
        return new SpawnConfig(

                // Traps
                DEFAULT_SPIKE_TRAP_MIN_LEVEL, DEFAULT_POISON_TRAP_MIN_LEVEL,
                DEFAULT_TELEPORT_TRAP_MIN_LEVEL, DEFAULT_TRAP_RATE,
                // Enemies
                DEFAULT_ENEMY_SPAWN_RATE, DEFAULT_MAX_ENEMIES_PER_ROOM,
                DEFAULT_HP_SCALING_ALPHA, DEFAULT_DAMAGE_SCALING_BETA,
                DEFAULT_XP_SCALING_LAMBDA,
                // Enemy weights
                DEFAULT_BAT_BASE_WEIGHT, DEFAULT_GOBLIN_BASE_WEIGHT,
                DEFAULT_DRAGON_BASE_WEIGHT);
    }

    /**
     * Creates a SpawnConfig with slightly higher rates for debugging/testing.
     *
     * @return debug spawn configuration with moderately increased rates
     */
    public static SpawnConfig debug() {
        return new SpawnConfig(
                // Traps from level 1
                1, 1, 1, DEBUG_TRAP_RATE,
                // Enemies
                DEBUG_ENEMY_SPAWN_RATE, DEFAULT_MAX_ENEMIES_PER_ROOM,
                DEFAULT_HP_SCALING_ALPHA, DEFAULT_DAMAGE_SCALING_BETA,
                DEFAULT_XP_SCALING_LAMBDA,
                // Enemy weights - equal for testing
                DEBUG_ENEMY_WEIGHT, DEBUG_ENEMY_WEIGHT,
                DEBUG_ENEMY_WEIGHT);
    }

    /**
     * Scales base HP for a given dungeon level.
     * Formula: HP(L) = HP_base * (1 + alpha*(L-1))
     *
     * @param baseHP the base HP value
     * @param level  the dungeon level (1-indexed)
     * @return scaled HP value
     */
    public int scaleHP(final int baseHP, final int level) {
        return (int) (baseHP * (1 + hpScalingAlpha * (level - 1)));
    }

    /**
     * Scales base damage for a given dungeon level.
     * Formula: DMG(L) = DMG_base + floor(beta*(L-1))
     *
     * @param baseDamage the base damage value
     * @param level      the dungeon level (1-indexed)
     * @return scaled damage value
     */
    public int scaleDamage(final int baseDamage, final int level) {
        return baseDamage + (int) Math.floor(damageScalingBeta * (level - 1));
    }

    /**
     * Calculates XP required to reach the next level.
     * Formula: XP_next(L) = 50*L^2 - 50*L + 100
     *
     * @param currentLevel the player's current level
     * @return XP needed for next level
     */
    public int xpForNextLevel(final int currentLevel) {
        return XP_MULTIPLIER * currentLevel * currentLevel
                - XP_MULTIPLIER * currentLevel + XP_BASE;
    }

    /**
     * Calculates XP gained from defeating an enemy.
     * Formula: XP_gain = XP_base * (1 + lambda*(L-1))
     *
     * @param baseXP       the enemy's base XP value
     * @param monsterLevel the dungeon level where the monster resides
     * @return XP gained from defeating this enemy
     */
    public int xpGain(final int baseXP, final int monsterLevel) {
        return (int) (baseXP * (1 + xpScalingLambda * (monsterLevel - 1)));
    }

    /**
     * Calculates the adjusted food/potion spawn rate for a given level.
     * Rate decreases by foodPotionDecayPerLevel for each level.
     *
     * @param level the dungeon level
     * @return adjusted spawn rate (minimum 0)
     */
    /*public double getFoodPotionRate(final int level) {
        return Math.max(0, foodPotionBaseRate - (level - 1) * foodPotionDecayPerLevel);
    }*/

    /**
     * Calculates enemy weight for level-based weighted selection.
     * Stronger enemies become more likely at deeper levels.
     * Weight = baseWeight + (level - 1) * levelBonus
     * levelBonus: Bat=0, Goblin=1, Dragon=2
     *
     * @param enemyType 0=Bat, 1=Goblin, 2=Dragon
     * @param level     the dungeon level
     * @return effective weight for enemy selection
     */
    public int getEnemyWeight(final int enemyType, final int level) {
        return switch (enemyType) {
            case 0 -> batBaseWeight; // Bat doesn't scale up
            case 1 -> goblinBaseWeight + level - 1;
            case 2 -> dragonBaseWeight + 2 * (level - 1);
            default -> throw new IllegalArgumentException("Unknown enemy type: " + enemyType);
        };
    }
}
