package it.unibo.Jrogue.engine;

/*
* The idea behind ScalableContentPane is that when the user resize the windowed GUI it scales the graphics correctly.
* In order to do so the Pane can zoom in or zoom out keeping the 16:9 pixel ratio, while the window changing size fills
* the space between the Pane and the Window. To see it more clearly it is possible to change color in
* ScalaleContentPane.setStyle.
*
* */

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;

public class ScalableContentPane extends StackPane {

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;
    private Pane contentPane;

    public ScalableContentPane(Pane content) {
        this.setStyle("-fx-background-color: #000000;");
        setContent(content);

        this.widthProperty().addListener((obs, oldVal, newVal) -> resize());
        this.heightProperty().addListener((obs, oldVal, newVal) -> resize());
    }

    public void setContent(Pane newContent) {
        this.getChildren().clear();
        this.contentPane = newContent;

        newContent.setPrefSize(WIDTH, HEIGHT);
        newContent.setMinSize(WIDTH, HEIGHT);
        newContent.setMaxSize(WIDTH, HEIGHT);
        this.getChildren().add(newContent);
        resize();
    }


    private void resize() {
        if (contentPane == null) return;
        double windowWidth = getWidth();
        double windowHeight = getHeight();
        double scaleX = windowWidth / WIDTH;
        double scaleY = windowHeight / HEIGHT;
        double scaleFactor = Math.min(scaleX, scaleY);
        Scale scale = new Scale(scaleFactor, scaleFactor);
        //We zoom from the center
        scale.setPivotX(WIDTH / 2.0);
        scale.setPivotY(HEIGHT / 2.0);

        contentPane.getTransforms().setAll(scale);
    }
}