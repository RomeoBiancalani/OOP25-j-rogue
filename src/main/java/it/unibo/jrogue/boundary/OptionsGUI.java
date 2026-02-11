package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * Boundary for the Options Menu.
 * */
public final class OptionsGUI implements MenuGUI {
    private static final int BUTTONS_WIDTH = 400;
    private static final int FONT_SIZE = 35;
    private static final int BUTTONS_SPACING = 10;

    private final List<Button> buttonsOptions;
    private final VBox rootLayout;
    private final String[] optionsName = {"Fullscreen: OFF", "Back"};
    /**
     * Initializing Options GUI.
     * */

    public OptionsGUI() {
        rootLayout = new VBox(BUTTONS_SPACING);
        buttonsOptions = new ArrayList<>();
        initGraphics();
    }

    @Override
    public void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");

        for (final String name : optionsName) {
            final Button button = new Button(name);
            button.setFont(Font.font("Consolas", FONT_SIZE));
            button.setMinWidth(BUTTONS_WIDTH);
            button.setFocusTraversable(false);
            buttonsOptions.add(button);
            rootLayout.getChildren().add(button);
        }
    }

    @Override
    public void updateSelection(final int selectIndex) {
        for (int i = 0; i < buttonsOptions.size(); i++) {
            final Button button = buttonsOptions.get(i);
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
    /**
     * Update the graphics when fullscreen button is toggled.
     *
     * @param isFull which indicates whether the fullscreen is already on or off*/

    public void updateFullscreenText(final boolean isFull) {
        final Button button = buttonsOptions.getFirst();
        final String fullscreenButtonText = isFull ? "Fullscreen: ON" : "Fullscreen: OFF";
        button.setText(fullscreenButtonText);
        updateSelection(0);
    }

    @Override
    public VBox getLayout() {
        return rootLayout;
    }
}
