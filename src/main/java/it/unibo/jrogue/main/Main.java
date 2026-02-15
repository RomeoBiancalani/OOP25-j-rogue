package it.unibo.jrogue.main;

/**
 * This file is a "Fake" main to bypass JavaFX unnamed modules check.
 */

public final class Main {
    /**
     * Empty constructor for the checkstyle .
     * */

    private Main() {

    }
    /**
     * @param args main.
     */

    public static void main(final String[] args) {
        Launch.launcher(args);
    }
}
