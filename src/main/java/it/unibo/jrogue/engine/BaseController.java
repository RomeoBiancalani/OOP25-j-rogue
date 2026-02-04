package it.unibo.jrogue.engine;

import it.unibo.jrogue.controller.GameController;
import it.unibo.jrogue.controller.InputHandler;
import it.unibo.jrogue.controller.MenuController;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import it.unibo.jrogue.controller.PauseGameController;

public final class BaseController {

    private GameState entity;
    private Stage primaryStage;
    private ScalableContentPane scalingContainer;

    /*These are all the Controller we need and made*/
    private InputHandler currentHandler;
    private InputHandler menuController;
    private InputHandler gameController;
    private InputHandler pauseController;


    public BaseController( GameState entity) {
        this.entity = entity;
        this.menuController = new MenuController(this);
        this.gameController = new GameController(this);
        this.pauseController = new PauseGameController(this);
        this.currentHandler = menuController;
    }

    public void setup(final Stage stage, final ScalableContentPane container) {
        this.primaryStage = stage;
        this.scalingContainer = container;
        changeView(menuController.getView());
    }

    public void handleGlobalKeyPress(final KeyEvent event) {
        if (currentHandler != null) {
            currentHandler.handleInput(event);
        }
    }

    public void changeView(final Pane newView) {
        scalingContainer.setContent(newView);
    }

    public void startGame() {
        entity.setCurrentState(GameState.State.PLAYING);
        this.currentHandler = gameController;
        changeView(gameController.getView());
    }

    public boolean toggleFullscreen() {
        final boolean isFull = primaryStage.isFullScreen();
        primaryStage.setFullScreen(!isFull);
        return !isFull;
    }
    /*Need this later for GameOver state*/
    public void backToMainMenu() {
        entity.setCurrentState(GameState.State.MAIN_MENU);
        this.currentHandler = menuController;
        changeView(menuController.getView());
    }

    public void pauseGame(){
        this.currentHandler=pauseController;
        changeView(pauseController.getView());
    }

    public void resumeGame(){
        this.currentHandler=gameController;
        changeView(gameController.getView());
    }

}
