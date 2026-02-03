package it.unibo.rogue.entity;

import java.util.Random;

/**
 * Utility class to simulate dice rolls.
 */
public final class Dice {

    private static final Random RAND = new Random();

    /**
     * Private constructor to prevent instantion of this utility class.
     */
    private Dice() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Sets the seed for the random number generator.
     * This enables deterministic dice rolls for reproducible level generation.
     *
     * @param seed the seed value for random generation
     */
    public static void setSeed(final long seed) {
        RAND.setSeed(seed);
    }

    /**
     * Returns the Random instance used by this class.
     *
     * @return the Random instance
     */
    public static Random getRandom() {
        return RAND;
    }

    /**
     * Simulates rolling a specified number of dice with a given number of sides.
     * 
     * @param nDice The number of dice to roll.
     * @param sides The number of faces.
     * @return The sum of all dice rolls.
     * @throws IllegalArgumentException if nDice or sides are less than 1.
     */
    public static int roll(final int nDice, final int sides) {
        if (nDice < 1 || sides < 1) {
            throw new IllegalArgumentException("nDice and sides must be positive");
        }

        int total = 0;
        for (int i = 0; i < nDice; i++) {
            total += RAND.nextInt(sides) + 1;
        }
        return total;
    }
}
