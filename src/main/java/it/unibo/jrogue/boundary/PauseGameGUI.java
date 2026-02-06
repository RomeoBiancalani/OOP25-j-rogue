package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;
/*Boundary for Pause menu when in game*/

public final class PauseGameGUI {
    private final List<Button> buttonsPause;
    private final VBox rootLayout;

    private final String[] buttonsName = {"Save Game", "Back to Menu", "Return"};
    /*Builder*/

    public PauseGameGUI() {
        rootLayout = new VBox(10);
        buttonsPause = new ArrayList<>();
        initGraphics();
    }
    /*Initialize the graphics settings of the pause menu*/

    private void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");

        for (final String name : buttonsName) {
            final Button button = new Button(name);
            button.setFont(Font.font("Consolas", 35));
            button.setMinWidth(400);
            button.setFocusTraversable(false);
            buttonsPause.add(button);
            rootLayout.getChildren().add(button);
        }
    }
    /*Update graphic based on button selected*/

    public void updateSelection(final int selectIndex) {
        for (int i = 0; i < buttonsPause.size(); i++) {
            final Button button = buttonsPause.get(i);
            if (i == selectIndex) {
                final String text = button.getText().replace(">", "").replace("<", "").trim();
                button.setText("> " + text + " <");
                button.setStyle("-fx-base: #b6e7b8;");
            } else {
                final String text = button.getText().replace(">", "").replace("<", "").trim();
                button.setText(text);
                button.setStyle("");
            }
        }
    }

    /*return the layout of the Pause menu*/

    public VBox getLayout() {
        return rootLayout;
    }
}

