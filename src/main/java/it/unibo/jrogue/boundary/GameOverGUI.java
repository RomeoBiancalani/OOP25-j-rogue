package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

/**
 * This class refers to the GameOver Boundary
 * */
public class GameOverGUI {
    private static final String BACKGROUND_PATH = "GameOver.png";
    private final VBox rootLayout;

    /**
     * Constructor for GameOverGUI.
     * */
    public GameOverGUI() {
        this.rootLayout = new VBox();
        initGraphics();
    }

    /**
     * Initialization of the Game over Background.
     *  */
    public void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        Image backgroundImage = new Image(getClass().getResourceAsStream("/" + BACKGROUND_PATH));
        BackgroundImage background = new BackgroundImage(
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
