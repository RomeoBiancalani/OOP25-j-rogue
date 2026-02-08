package it.unibo.jrogue.controller;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Interface for every Controller with base method required*/
public interface InputHandler {
    /**
     * Takes a key event to associate to a function,
     * specific methods are needed to perform actions
     *
     * @param  event which is a keyboard key
     */

    void handleInput(KeyEvent event);
    /**
    * @return a Pane for the GUI
    * */

    Pane getView();
}
