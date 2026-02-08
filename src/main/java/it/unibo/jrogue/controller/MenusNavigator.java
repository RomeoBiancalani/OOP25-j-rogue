package it.unibo.jrogue.controller;

import java.util.function.Consumer;
/**
* This class is most likely to be removed to make a better Menu navigator that works for every menu.
* */

public final class MenusNavigator {
    private int currentIndex;
    private final int maxItems;
    private final Consumer<Integer> onUpdateGraphics;

    /**
     * Initialization*/
    public MenusNavigator(final int maxItems, final Consumer<Integer> onUpdateGraphics) {
        this.maxItems = maxItems;
        this.onUpdateGraphics = onUpdateGraphics;
    }
    /**
     * If possible move the cursor up and update the graphics*/

    public void navigateUp() {
        if (currentIndex > 0) {
            currentIndex--;
            update();
        }
    }
    /**
     * If possible move the cursor down and update the graphics*/

    public void navigateDown() {
        if (currentIndex < maxItems - 1) {
            currentIndex++;
            update();
        }
    }
    /**
     * Get the index where the cursor is on*/

    public int getSelection() {
        return currentIndex;
    }
    /**Call the update for the graphics*/

    public void update() {
        onUpdateGraphics.accept(currentIndex);
    }
}
