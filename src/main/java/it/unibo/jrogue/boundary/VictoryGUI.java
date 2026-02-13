package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

/**
 * This class refers to the GameOver Boundary
 * */
public class VictoryGUI {
    private static final String BACKGROUND_PATH = "VictoryImage.png";
    private final VBox rootLayout;

    /**
     * Constructor for VictoryGUI.
     * */
    public VictoryGUI() {
        this.rootLayout = new VBox();
        initGraphics();
    }

    /**
     * Initialization of the Victory Background.
     *  */
    public void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        final Image backgroundImage = new Image(getClass().getResourceAsStream("/" + BACKGROUND_PATH));
        final BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );
        rootLayout.setBackground(new Background(background));
    }
    /**
     * getter for the layout.
     *
     * @return rootLayout which contain the GUI elements
     * */

    public VBox getLayout() {
        return rootLayout;
    }
}
