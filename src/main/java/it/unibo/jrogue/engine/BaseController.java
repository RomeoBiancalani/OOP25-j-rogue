package it.unibo.jrogue.engine;

import it.unibo.jrogue.controller.DungeonController;
import it.unibo.jrogue.controller.GameController;
import it.unibo.jrogue.controller.InputHandler;
import it.unibo.jrogue.controller.InventoryController;
import it.unibo.jrogue.controller.MenuController;
import it.unibo.jrogue.controller.OptionsController;
import it.unibo.jrogue.controller.PauseGameController;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * BaseController class handle every controller the software utilize
 * and some useful utility methods.
 */

public final class BaseController {

    private final GameState entity;
    private Stage primaryStage;
    private ScalableContentPane scalingContainer;

    private InputHandler currentController;
    private final InputHandler menuController;
    private final InputHandler optionsController;
    private final InputHandler gameController;
    private final InputHandler pauseController;
    private final InputHandler inventoryController;

    /**
     * Controllers initialization.
     *
     * @param entity which is the game entity.
     */
    public BaseController(final GameState entity) {
        this.entity = entity;
        this.menuController = new MenuController(this);
        this.optionsController = new OptionsController(this);
        this.gameController = new GameController(this);
        this.pauseController = new PauseGameController(this);
        this.inventoryController = new InventoryController(this);
        this.currentController = menuController;
    }

    /**
     * Setting up the stage and container in order to be viewable.
     *
     * @param stage     which is the main container for the software.
     *
     * @param container which is the Pane utilized for scaling graphics.
     */

    public void setup(final Stage stage, final ScalableContentPane container) {
        this.primaryStage = stage;
        this.scalingContainer = container;
        changeView(menuController.getView());
    }

    /**
     * Giving to the current controller the handling of the KeyEvents.
     *
     * @param event which is a KeyEvent.
     */

    public void handleGlobalKeyPress(final KeyEvent event) {
        if (currentController != null) {
            currentController.handleInput(event);
        }
    }

    /**
     * Changing the current Pane to display.
     *
     * @param newView which is the new Pane that must be viewed.
     */

    public void changeView(final Pane newView) {
        scalingContainer.setContent(newView);
    }

    /**
     * Initialize the game with both the controller and view.
     */

    public void startGame() {
        entity.setCurrentState(GameState.State.PLAYING);
        ((GameController) gameController).startNewGame();
        this.currentController = gameController;
        changeView(gameController.getView());
    }

    /**
     * Activate fullscreen mode on the stage.
     *
     * @return !isFull which is the opposite of the previous status.
     */

    public boolean toggleFullscreen() {
        final boolean isFull = primaryStage.isFullScreen();
        primaryStage.setFullScreen(!isFull);
        return !isFull;
    }

    /**
     * Change controllers and view to get back to main menu.
     */

    public void backToMainMenu() {
        entity.setCurrentState(GameState.State.MAIN_MENU);
        this.currentController = menuController;
        changeView(menuController.getView());
    }

    /**
     * Gives the possibility to get back to main menu or get back to pause menu.
     */

    public void goBack() {
        if (entity.getCurrentState() == GameState.State.PLAYING) {
            this.currentController = pauseController;
            changeView(pauseController.getView());
        } else {
            entity.setCurrentState(GameState.State.MAIN_MENU);
            this.currentController = menuController;
            changeView(menuController.getView());
        }
    }

    /**
     * Change controller and view to open options menu.
     */

    public void goToOptions() {
        this.currentController = optionsController;
        changeView(optionsController.getView());
    }

    /**
     * Change controller and view to open Pause while in game.
     */

    public void pauseGame() {
        this.currentController = pauseController;
        changeView(pauseController.getView());
    }

    /**
     * Opening the Inventory while in game.
     */

    public void openInventory() {
        this.currentController = inventoryController;
        changeView(inventoryController.getView());
    }

    /**
     * Change controller and view when in Pause to get back to the game.
     */

    public void resumeGame() {
        this.currentController = gameController;
        changeView(gameController.getView());
    }

    /**
     * Saves the current game to the default save file.
     *
     * @return true if the save was successful.
     */
    public boolean saveGame() {
        try {
            final GameController gc = (GameController) gameController;
            SaveManager.save(gc.getDungeonController(), SaveManager.getDefaultSavePath());
            return true;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * Loads a game from the default save file.
     *
     * @return true if the load was successful.
     */
    public boolean loadGame() {
        try {
            final SaveData data = SaveManager.load(SaveManager.getDefaultSavePath());
            final GameController gc = (GameController) gameController;
            final DungeonController restored = SaveManager.restore(data, gc.getRenderer());
            gc.restoreGame(restored);

            entity.setCurrentState(GameState.State.PLAYING);
            this.currentController = gameController;
            changeView(gameController.getView());
            return true;
        } catch (final IOException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if a save file exists.
     *
     * @return true if a save file exists.
     */
    public boolean hasSaveFile() {
        return SaveManager.saveExists();
    }

    /**
     * Provides with the InventoryController.
     * 
     * @return the InventoryController.
     */
    public InputHandler getInventoryController() {
        return this.inventoryController;
    }
}
