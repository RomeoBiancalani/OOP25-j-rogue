package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.text.FontWeight;

/**
 * This class refers to the GameOver Boundary.
 * */
public class GameOverGUI {
    private static final String BACKGROUND_PATH = "GameOver.png";
    private final VBox rootLayout;
    private final Label scoreLabel;

    /**
     * Constructor for GameOverGUI.
     * */
    public GameOverGUI() {
        this.rootLayout = new VBox();
        this.scoreLabel = new Label();
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
        scoreLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 30));
        scoreLabel.setTextFill(Color.GOLD);
        scoreLabel.setEffect(new DropShadow(10, Color.BLACK));
        rootLayout.getChildren().add(scoreLabel);
    }

    public void setScoreLabel(int score){
        this.scoreLabel.setText("Score: " + score);
    }

    /**
     * getter for the layout.
     *
     * @return rootLayout which contain the GUI elements.
     * */

    public VBox getLayout() {
        return rootLayout;
    }
}