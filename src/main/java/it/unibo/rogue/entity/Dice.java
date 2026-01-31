package it.unibo.rogue.entity;

import java.util.Random;

public final class Dice {

    private static final Random rand = new Random();

    public static int roll(final int nDice, final int sides){
        int total = 0;
        for (int i = 0; i < nDice; i++) {
            total += rand.nextInt(sides) + 1;
        }
        return total;
    }
}
