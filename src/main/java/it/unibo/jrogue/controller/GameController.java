package it.unibo.jrogue.controller;

import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class GameController implements InputHandler {
    private final BaseController controller;
    private final Pane gameView;

    public GameController(final BaseController controller) {
        this.controller = controller;
        this.gameView = new Pane(); //Temporary Pane waiting for the team to make Game GUI
        this.gameView.setStyle("-fx-background-color: #ffffff;");
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W)  {
            //moveUp();
        } else if (code == KeyCode.A) {
            //moveLeft();
        } else if (code == KeyCode.D) {
            //moveRight();
        } else if (code == KeyCode.S) {
            //moveDown();
        } else if (code == KeyCode.Q) {
            //openInventory();
        } else if (code == KeyCode.E) {
            //nextLevel(); This method take action when the player want to interact with a stair for the next level
        } else if (code == KeyCode.SPACE) {
            //readDialogue(); This method refers to text boxes, when already in a text box continue the dialogue
        } else if (code == KeyCode.CONTROL) {
            //turnAcceleration();
        } else if (code == KeyCode.SHIFT) {
            //useDistanceWeapon();
        } else if (code == KeyCode.ESCAPE) { //This is not an actual key that we keep, just to see if it works
            controller.pauseGame();
        }
    }

    @Override
    public Pane getView() {
        return this.gameView;//.getLayout();
    }
}
