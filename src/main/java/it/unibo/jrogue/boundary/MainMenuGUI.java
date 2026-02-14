package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Boundary for the MainMenu.
 */
public final class MainMenuGUI implements MenuGUI {
    private static final int BUTTONS_WIDTH = 400;
    private static final int FONT_SIZE = 35;
    private static final int BUTTONS_SPACING = 10;
    private static final String BACKGROUND_PATH = "jrogueMenu.png";
    private static final String[] BUTTONS_NAME = { "New Game", "Load Game", "Options", "Exit" };

    private final List<Button> buttonsMenu;
    private final VBox rootLayout;

    /**
     * GUI initialization.
     */

    public MainMenuGUI() {
        rootLayout = new VBox(BUTTONS_SPACING);
        buttonsMenu = new ArrayList<>();
        initGraphics();
    }

    @Override
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

        for (final String name : BUTTONS_NAME) {
            final Button button = new Button(name);
            button.setFont(Font.font("Consolas", FONT_SIZE));
            button.setMinWidth(BUTTONS_WIDTH);
            button.setFocusTraversable(false);
            buttonsMenu.add(button);
            rootLayout.getChildren().add(button);
        }
    }

    @Override
    public void updateSelection(final int selectIndex) {
        for (int i = 0; i < buttonsMenu.size(); i++) {
            final Button button = buttonsMenu.get(i);
            if (i == selectIndex) {
                button.setText("> " + BUTTONS_NAME[i] + " <");
                button.setStyle("-fx-base: #e4ea5f;");
            } else {
                button.setText(BUTTONS_NAME[i]);
                button.setStyle("");
            }
        }
    }

    @Override
    public VBox getLayout() {
        return rootLayout;
    }
}
