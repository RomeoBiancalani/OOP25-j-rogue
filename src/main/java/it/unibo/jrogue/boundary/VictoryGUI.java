package it.unibo.jrogue.boundary;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

/**
 * This class refers to the GameOver Boundary.
 */
public final class VictoryGUI {
    private static final String BACKGROUND_PATH = "VictoryImage.png";
    private final VBox rootLayout;
    private final Label scoreLabel;

    /**
     * Constructor for VictoryGUI.
     */
    public VictoryGUI() {
        this.rootLayout = new VBox();
        this.scoreLabel = new Label();
        initGraphics();
    }

    /**
     * Initialization of the Victory Background.
     */
    private void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        final int fontThickness = 30;
        final Image backgroundImage = new Image(getClass().getResourceAsStream("/" + BACKGROUND_PATH));
        final BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        rootLayout.setBackground(new Background(background));
        scoreLabel.setFont(Font.font("Consolas", FontWeight.BOLD, fontThickness));
        scoreLabel.setTextFill(Color.GOLD);
        scoreLabel.setEffect(new DropShadow(10, Color.BLACK));
        rootLayout.getChildren().add(scoreLabel);
    }
    /**
     * Set the final score to be displayed on the GameOver screen.
     *
     * @param score simply the score of that game.
     * */

    public void setScoreLabel(final int score) {
        this.scoreLabel.setText("Score: " + score);
    }

    /**
     * getter for the layout.
     *
     * @return rootLayout which contain the GUI elements.
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "The return of the real Layout is needed."
    )
    public VBox getLayout() {
        return rootLayout;
    }
}
