package it.unibo.jrogue.engine;

/**
* The idea behind ScalableContentPane is that when the user resize the windowed GUI it scales the graphics correctly.
* In order to do so the Pane can zoom in or zoom out keeping the 16:9 pixel ratio, while the window changing size fills
* the space between the Pane and the Window. To see it more clearly it is possible to change color in
* ScalaleContentPane.setStyle.
*
* */

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;

/**
 * Scalable content Pane, resize the Pane when the user resize the application keeping the 16:9 ratio */

public final class ScalableContentPane extends StackPane {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private Pane contentPane;

    /**
     * Initialization, creates black borders around the Pane
     *
     * @param content which is the content of the Pane that we want to resize and scale */

    public ScalableContentPane(final Pane content) {
        this.setStyle("-fx-background-color: #000000;");
        setContent(content);

        this.widthProperty().addListener((obs, oldVal, newVal) -> resize());
        this.heightProperty().addListener((obs, oldVal, newVal) -> resize());
    }

    /**
     * Set the new Pane to display
     *
     * @param newContent which is the Pane that we force to scale its graphics*/

    public void setContent(final Pane newContent) {
        this.getChildren().clear();
        this.contentPane = newContent;

        newContent.setPrefSize(WIDTH, HEIGHT);
        newContent.setMinSize(WIDTH, HEIGHT);
        newContent.setMaxSize(WIDTH, HEIGHT);
        this.getChildren().add(newContent);
        resize();
    }
    /**
     * Do the math for the resize and apply the zoom to the Pane*/

    private void resize() {
        if (contentPane == null) {
            return;
        }
        final double windowWidth = getWidth();
        final double windowHeight = getHeight();
        final double scaleX = windowWidth / WIDTH;
        final double scaleY = windowHeight / HEIGHT;
        final double scaleFactor = Math.min(scaleX, scaleY);
        final Scale scale = new Scale(scaleFactor, scaleFactor);
        scale.setPivotX(WIDTH / 2.0);
        scale.setPivotY(HEIGHT / 2.0);
        contentPane.getTransforms().setAll(scale);
    }
}
