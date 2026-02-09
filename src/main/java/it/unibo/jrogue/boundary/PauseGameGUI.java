package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * Boundary for Pause menu when in game.
 * */

public final class PauseGameGUI implements MenuGUI {
    private static final int BUTTONS_WIDTH = 400;
    private static final int FONT_SIZE = 35;
    private static final int BUTTONS_SPACING = 10;
    private final List<Button> buttonsPause;
    private final VBox rootLayout;
    private final String[] buttonsName = {"Save Game", "Options", "Back to Menu", "Return"};
    /**
     * Initializing Pause GUI.
     * */

    public PauseGameGUI() {
        rootLayout = new VBox(BUTTONS_SPACING);
        buttonsPause = new ArrayList<>();
        initGraphics();
    }

    @Override
    public void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");

        for (final String name : buttonsName) {
            final Button button = new Button(name);
            button.setFont(Font.font("Consolas", FONT_SIZE));
            button.setMinWidth(BUTTONS_WIDTH);
            button.setFocusTraversable(false);
            buttonsPause.add(button);
            rootLayout.getChildren().add(button);
        }
    }

    @Override
    public void updateSelection(final int selectIndex) {
        for (int i = 0; i < buttonsPause.size(); i++) {
            final Button button = buttonsPause.get(i);
            if (i == selectIndex) {
                final String text = button.getText().replace(">", "").replace("<", "").trim();
                button.setText("> " + text + " <");
                button.setStyle("-fx-base: #e4ea5f;");
            } else {
                final String text = button.getText().replace(">", "").replace("<", "").trim();
                button.setText(text);
                button.setStyle("");
            }
        }
    }

    @Override
    public VBox getLayout() {
        return rootLayout;
    }
}

