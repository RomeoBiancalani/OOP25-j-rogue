package it.unibo.Jrogue.main;
/*
* This file can change if the JavaFX module check is fixed
*
* */




import it.unibo.Jrogue.engine.BaseController;
import it.unibo.Jrogue.engine.GameState;
import it.unibo.Jrogue.engine.ScalableContentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Launch extends Application {

    @Override
    public void start(Stage primaryStage) {
        /*Initializing*/
        GameState entity = new GameState();
        BaseController controller = new BaseController(entity);
        ScalableContentPane rootContainer = new ScalableContentPane(new Pane());
        controller.setup(primaryStage, rootContainer);
        /*By changing the values, when the software launch it opens at the selected resolution*/
        Scene globalScene = new Scene(rootContainer, 1280, 720);
        /*The BaseController handle everything*/
        globalScene.setOnKeyPressed(event -> controller.handleGlobalKeyPress(event));

        /*Window settings*/
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setTitle("J-ROGUE");
        primaryStage.setScene(globalScene);
        primaryStage.show();
    }

    public static void Launcher(String[] args) {
        launch(args);
    }
}