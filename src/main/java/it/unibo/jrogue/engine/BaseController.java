package it.unibo.jrogue.engine;

import it.unibo.jrogue.controller.GameController;
import it.unibo.jrogue.controller.InputHandler;
import it.unibo.jrogue.controller.MenuController;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import it.unibo.jrogue.controller.PauseGameController;

/*BaseController class handle every controller the software utilize
* and some useful utility methods */
public final class BaseController {

    private final GameState entity;
    /*Resolution portability */
    private Stage primaryStage;
    private ScalableContentPane scalingContainer;

    /*These are all the Controller needed*/
    private InputHandler currentController;
    private final InputHandler menuController;
    private final InputHandler gameController;
    private final InputHandler pauseController;

    /*
    * Controllers initialization
    * */
    public BaseController(final GameState entity) {
        this.entity = entity;
        this.menuController = new MenuController(this);
        this.gameController = new GameController(this);
        this.pauseController = new PauseGameController(this);
        this.currentController = menuController;
    }
    /*Setting up the stage and container in order to be viewable */

    public void setup(final Stage stage, final ScalableContentPane container) {
        this.primaryStage = stage;
        this.scalingContainer = container;
        changeView(menuController.getView());
    }
    /*Giving to the current controller the handling of the KeyEvents*/

    public void handleGlobalKeyPress(final KeyEvent event) {
        if (currentController != null) {
            currentController.handleInput(event);
        }
    }
    /*Changing the current Pane to display*/

    public void changeView(final Pane newView) {
        scalingContainer.setContent(newView);
    }
    /*Initialize the game with both the controller and view*/

    public void startGame() {
        entity.setCurrentState(GameState.State.PLAYING);
        this.currentController = gameController;
        changeView(gameController.getView());
    }
    /*Activate fullscreen mode on the stage*/

    public boolean toggleFullscreen() {
        final boolean isFull = primaryStage.isFullScreen();
        primaryStage.setFullScreen(!isFull);
        return !isFull;
    }
    /*Change controllers and view to get back to main menu*/

    public void backToMainMenu() {
        entity.setCurrentState(GameState.State.MAIN_MENU);
        this.currentController = menuController;
        changeView(menuController.getView());
    }
    /*Change controller and view to open Pause while in game*/

    public void pauseGame() {
        this.currentController = pauseController;
        changeView(pauseController.getView());
    }
    /*Change controller and view when in Pause to get back to the game*/

    public void resumeGame() {
        this.currentController = gameController;
        changeView(gameController.getView());
    }

}
