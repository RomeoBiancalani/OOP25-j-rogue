package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * Boundary for the MainMenu.*/
public final class MainMenuGUI implements MenuGUI {
    private static final int BUTTONS_WIDTH = 400;
    private static final int FONT_SIZE = 35;
    private static final int BUTTONS_SPACING = 10;

    private static final String[] BUTTONS_NAME = {"New Game", "Load Game", "Options", "Exit"};
    private final List<Button> buttonsMenu;
    private final VBox rootLayout;
    /**
     * GUI initialization.
     * */

    public MainMenuGUI() {
        rootLayout = new VBox(BUTTONS_SPACING);
        buttonsMenu = new ArrayList<>();
        initGraphics();
    }

    @Override
    public void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");
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
