package it.unibo.jrogue.controller;

import java.util.function.Consumer;
/*
* This class must be used for all the Menus in the software
* */
public class MenusNavigator {
    private int currentIndex = 0;
    private final int maxItems;
    private final Consumer<Integer> onUpdateGraphics;

    public MenusNavigator(int maxItems, Consumer<Integer> onUpdateGraphics) {
        this.maxItems = maxItems;
        this.onUpdateGraphics = onUpdateGraphics;
    }

    public void navigateUp() {
        if (currentIndex > 0) {
            currentIndex--;
            update();
        }
    }

    public void navigateDown() {
        if (currentIndex < maxItems - 1) {
            currentIndex++;
            update();
        }
    }

    public int getSelection() {
        return currentIndex;
    }

    public void update() {
        onUpdateGraphics.accept(currentIndex);
    }
}
