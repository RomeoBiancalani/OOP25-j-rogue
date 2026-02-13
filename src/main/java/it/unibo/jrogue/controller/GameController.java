package it.unibo.jrogue.controller;

import it.unibo.jrogue.boundary.DungeonRenderer;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.entity.entities.api.Player;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Controller that handles the actions the player can perform during the game.
 */
public final class GameController implements InputHandler {

    private final BaseController controller;
    private final DungeonRenderer renderer;
    private DungeonController dungeonController;

    /**
     * Initialize the controller with a new dungeon renderer.
     *
     * @param controller which is the BaseController we communicate with
     */
    public GameController(final BaseController controller) {
        this.controller = controller;
        this.renderer = new DungeonRenderer();
    }

    /**
     * Starts a new game with a random seed.
     */
    public void startNewGame() {
        final long seed = System.currentTimeMillis();
        this.dungeonController = new DungeonController(seed, renderer);
        dungeonController.startNewGame();
    }

    /**
     * Restores a game from a saved dungeon controller state.
     *
     * @param restored the restored dungeon controller
     */
    public void restoreGame(final DungeonController restored) {
        this.dungeonController = restored;
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            dungeonController.executeTurn(Move.UP);
        } else if (code == KeyCode.A) {
            dungeonController.executeTurn(Move.LEFT);
        } else if (code == KeyCode.D) {
            dungeonController.executeTurn(Move.RIGHT);
        } else if (code == KeyCode.S) {
            dungeonController.executeTurn(Move.DOWN);
        } else if (code == KeyCode.I) {
            controller.openInventory();
        } else if (code == KeyCode.E) {
            handleStairs();
        } else if (code == KeyCode.ESCAPE) {
            controller.pauseGame();
        } else if (code == KeyCode.SPACE) {
            //readDialogue(); This method refers to text boxes, when already in a text box continue the dialogue
        } else if (code == KeyCode.CONTROL) {
            //turnAcceleration();
        } else if (code == KeyCode.SHIFT) {
            //useDistanceWeapon();
        }
    }

    /**
     * Returns the player entity.
     *
     * @return the player
     */
    public Player getPlayer() {
        return dungeonController.getPlayer();
    }

    /**
     * Returns the dungeon controller for save/load access.
     *
     * @return the dungeon controller
     */
    public DungeonController getDungeonController() {
        return dungeonController;
    }

    /**
     * Returns the dungeon rendered for load.
     *
     * @return the dungeon renderer
     */
    public DungeonRenderer getRenderer() {
        return this.renderer;
    }

    @Override
    public Pane getView() {
        return this.renderer;
    }

    private void handleStairs() {
        if (dungeonController.isOnStairs()) {
            if (dungeonController.getCurrentLevel() >= DungeonController.MAX_LEVEL) {
                // TODO: Game is finished, display finish screen
                controller.backToMainMenu();
            } else {
                dungeonController.nextLevel();
            }
        }
    }
}
