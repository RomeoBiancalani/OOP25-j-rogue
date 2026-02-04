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

    private final int width = 1920;
    private final int height = 1080;
    private Pane contentPane;

    public ScalableContentPane(final Pane content) {
        this.setStyle("-fx-background-color: #000000;");
        setContent(content);

        this.widthProperty().addListener((obs, oldVal, newVal) -> resize());
        this.heightProperty().addListener((obs, oldVal, newVal) -> resize());
    }

    public final void setContent(final Pane newContent) {
        this.getChildren().clear();
        this.contentPane = newContent;

        newContent.setPrefSize(width, height);
        newContent.setMinSize(width, height);
        newContent.setMaxSize(width, height);
        this.getChildren().add(newContent);
        resize();
    }

    private void resize() {
        if (contentPane == null) return;
        double windowWidth = getWidth();
        double windowHeight = getHeight();
        double scaleX = windowWidth / width;
        double scaleY = windowHeight / height;
        double scaleFactor = Math.min(scaleX, scaleY);
        Scale scale = new Scale(scaleFactor, scaleFactor);
        //We zoom from the center
        scale.setPivotX(width / 2.0);
        scale.setPivotY(height / 2.0);

        contentPane.getTransforms().setAll(scale);
    }
}
