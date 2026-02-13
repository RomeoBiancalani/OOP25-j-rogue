package it.unibo.jrogue.controller;

import it.unibo.jrogue.boundary.GameOverGUI;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class GameOverController implements InputHandler{
    private final BaseController controller;
    private final GameOverGUI gameOver = new GameOverGUI();

/**
 * This controller, handles the GameOver screen of the game.
 * */
    public GameOverController(final BaseController controller){
        this.controller = controller;
    }

    @Override
    public void handleInput(final KeyEvent event){
        final KeyCode code = event.getCode();
        if (code == KeyCode.ENTER) {
            controller.backToMainMenu();
        }
    }

    @Override
    public Pane getView(){
        return gameOver.getLayout();
    }


}
