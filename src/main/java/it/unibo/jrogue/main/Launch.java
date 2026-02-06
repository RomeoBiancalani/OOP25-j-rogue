package it.unibo.jrogue.main;

/*
* This file can change if the JavaFX module check is fixed
*
* */

import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.engine.GameState;
import it.unibo.jrogue.engine.ScalableContentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class Launch extends Application {

    @Override
    public void start(final Stage primaryStage) {
        /*Initializing*/
        final GameState entity = new GameState();
        final BaseController controller = new BaseController(entity);
        final ScalableContentPane rootContainer = new ScalableContentPane(new Pane());
        controller.setup(primaryStage, rootContainer);
        /*By changing the values, when the software launch it opens at the selected resolution*/
        final Scene globalScene = new Scene(rootContainer, 1280, 720);
        /*The BaseController handle everything*/
        globalScene.setOnKeyPressed(controller::handleGlobalKeyPress);

        /*Window settings*/
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setTitle("J-ROGUE");
        primaryStage.setScene(globalScene);
        primaryStage.show();
    }

    public static void launcher(final String[] args) {
        launch(args);
    }
}
