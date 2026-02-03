package it.unibo.Jrogue.engine;

import it.unibo.Jrogue.controller.GameController;
import it.unibo.Jrogue.controller.InputHandler;
import it.unibo.Jrogue.controller.MenuController;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BaseController {

    private GameState entity;
    private Stage primaryStage;
    private ScalableContentPane scalingContainer;

    /*These are all the Controller we need and made*/
    private InputHandler currentHandler;
    private InputHandler MenuController;
    private InputHandler GameController;

    public BaseController(GameState entity) {
        this.entity = entity;
        this.MenuController = new MenuController(this);
        this.GameController= new GameController(this);
        this.currentHandler = MenuController;
    }

    public void setup(Stage stage, ScalableContentPane container) {
        this.primaryStage = stage;
        this.scalingContainer = container;
        changeView(MenuController.getView());
    }

    public void handleGlobalKeyPress(KeyEvent event) {
        if (currentHandler != null) {
            currentHandler.handleInput(event);
        }
    }

    public void changeView(Pane newView) {
        scalingContainer.setContent(newView);
    }

    public void startGame() {
        entity.setCurrentState(GameState.State.PLAYING);
        this.currentHandler = GameController;
        changeView(GameController.getView());
    }

    public boolean toggleFullscreen() {
        boolean isFull = primaryStage.isFullScreen();
        primaryStage.setFullScreen(!isFull);
        return !isFull;
    }
    /*Need this later for GameOver state*/
    public void backToMainMenu() {
        entity.setCurrentState(GameState.State.MAIN_MENU);
        this.currentHandler = MenuController;
        changeView(MenuController.getView());
    }


}
