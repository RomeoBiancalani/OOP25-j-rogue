package it.unibo.jrogue.main;

import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.engine.GameState;
import it.unibo.jrogue.engine.ScalableContentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This file can change if the JavaFX module check is fixed.
 */

public final class Launch extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final GameState entity = new GameState();
        final BaseController controller = new BaseController(entity);
        final ScalableContentPane rootContainer = new ScalableContentPane(new Pane());
        controller.setup(primaryStage, rootContainer);
        final Scene globalScene = new Scene(rootContainer, 1280, 720);
        globalScene.setOnKeyPressed(controller::handleGlobalKeyPress);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setTitle("J-ROGUE");
        primaryStage.setScene(globalScene);
        primaryStage.show();
    }

    /**
     * Launches the software.
     * 
     * @param args the args.
     */
    public static void launcher(final String[] args) {
        launch(args);
    }
}
