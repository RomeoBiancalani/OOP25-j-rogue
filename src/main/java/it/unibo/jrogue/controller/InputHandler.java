package it.unibo.Jrogue.controller;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public interface InputHandler {
    /*
    * Takes a key event to associate to a function,
    * thomas.bosi@studio.unibo.it will take care of this part
    * but needs the methods to perform actions
    * @param keyboard event
    */
    void handleInput(KeyEvent event);
    /*
    *
    * @return a Pane for the GUI
    * */
    Pane getView();
}
