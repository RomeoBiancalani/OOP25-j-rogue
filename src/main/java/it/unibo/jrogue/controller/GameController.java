package it.unibo.jrogue.controller;

import it.unibo.jrogue.WorldRenderingDemo;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.world.api.GameMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Controller that handles the actions the player can perform during the game.
 * */
public final class GameController implements InputHandler {
    private final BaseController controller;
    private final StackPane gameView;
    private final GameMap gameMap = null;

    /**
     * Initialize the controller, a temp Pane is added waiting for the actual Pane to be made.
     *
     * @param controller which is the BaseController we communicate with*/

    public GameController(final BaseController controller) {
        this.controller = controller;
        this.gameView = new WorldRenderingDemo();
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            //moveUp();
        } else if (code == KeyCode.A) {
            //moveLeft();
        } else if (code == KeyCode.D) {
            //moveRight();
        } else if (code == KeyCode.S) {
            //moveDown();
        } else if (code == KeyCode.I) {
            controller.openInventory();
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

    public Player getPlayer() {
        return this.gameMap.getPlayer().orElseThrow(() -> new IllegalStateException("Player not initialized"));
    }

    @Override
    public Pane getView() {
        return this.gameView;
    }
}
