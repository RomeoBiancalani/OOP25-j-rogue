package it.unibo.jrogue.controller;

import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jrogue.boundary.DungeonRenderer;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.entity.entities.api.Player;
import javafx.scene.image.Image;
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
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must be the same, i can't give a copy."
    )
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
        final InventoryController ic = (InventoryController) this.controller.getInventoryController();
        ic.setupPlayer(getPlayer(), getGameSprites());
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
            isDead();
        } else if (code == KeyCode.A) {
            dungeonController.executeTurn(Move.LEFT);
            isDead();
        } else if (code == KeyCode.D) {
            dungeonController.executeTurn(Move.RIGHT);
            isDead();
        } else if (code == KeyCode.S) {
            dungeonController.executeTurn(Move.DOWN);
            isDead();
        } else if (code == KeyCode.Q) {
            controller.openInventory();
        } else if (code == KeyCode.E) {
            handleStairs();
        } else if (code == KeyCode.ESCAPE) {
            controller.pauseGame();
        }
        if (getPlayer().hasWon()) {
            controller.victory();
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
     * Check if the player is dead, if it is then call the gameOver controller and GUI.
     * */
    private void isDead() {
        if (!dungeonController.getPlayer().isAlive()) {
            controller.gameOver();
        }
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
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Must return render for it to be displayed."
    )

    public DungeonRenderer getRenderer() {
        return this.renderer;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Must return render for it to be displayed."
    )

    @Override
    public Pane getView() {
        return this.renderer;
    }

    /**
     * Provides with all the sprites in the game.
     * 
     * @return a map with all the sprites saved.
     */
    public Map<String, Image> getGameSprites() {
        return this.renderer.getLoadedSprites();
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
