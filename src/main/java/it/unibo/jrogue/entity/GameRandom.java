package it.unibo.jrogue.entity;

import java.util.Random;

/**
 * Utility class that provides a centralized, deterministic source of randomness.
 */
public final class GameRandom {

    private static final Random RAND = new Random();

    /**
     * Private constructor to prevent instantion of this utility class.
     */
    private GameRandom() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Sets the seed for the random number generator.
     *
     * @param seed the seed value for random generation.
     */
    public static void setSeed(final long seed) {
        RAND.setSeed(seed);
    }

    /**
     * Returns the next random int value.
     * 
     * @return the next random integer.
     */
    public static int nextInt() {
        return RAND.nextInt();
    }

    /**
     * Returns the next random int value between 0 and the specified bounds.
     * 
     * @param bound the upper bound, must be positive.
     * @return the next random int between 0 and bound.
     * @throws IllegalArgumentExceptions if bounds is not positive.
     */
    public static int nextInt(final int bound) {
        return RAND.nextInt(bound);
    }

    /**
     * Returns the next random double value.
     * 
     * @return the next random double.
     */
    public static double nextDouble() {
        return RAND.nextDouble();
    }

    /**
     * Returns the next random double value between 0.0 and the specified bounds.
     * 
     * @param bound the upper bound, must be positive.
     * @return the next random double between 0.0 and bound.
     * @throws IllegalArgumentExceptions if bounds is not positive.
     */
    public static double nextDouble(final double bound) {
        return RAND.nextDouble(bound);
    }

    /**
     * Returns the next boolean values.
     * 
     * @return the next random boolean.
     */
    public static boolean nextBoolean() {
        return RAND.nextBoolean();
    }

}
