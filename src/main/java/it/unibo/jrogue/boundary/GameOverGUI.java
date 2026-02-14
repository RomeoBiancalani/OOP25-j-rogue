package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

/**
 * This class refers to the GameOver Boundary.
 */
public class GameOverGUI {
    private static final String BACKGROUND_PATH = "GameOver.png";
    private final VBox rootLayout;

    /**
     * Constructor for GameOverGUI.
     */
    public GameOverGUI() {
        this.rootLayout = new VBox();
        initGraphics();
    }

    /**
     * Initialization of the Game over Background.
     */
    public void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        final Image backgroundImage = new Image(getClass().getResourceAsStream("/" + BACKGROUND_PATH));
        final BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        rootLayout.setBackground(new Background(background));
    }

    /**
     * getter for the layout.
     *
     * @return rootLayout which contain the GUI elements.
     */

    public VBox getLayout() {
        return rootLayout;
    }
}
