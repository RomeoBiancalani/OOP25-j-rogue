package it.unibo.Jrogue.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

public class OptionsGUI {

    private List<Button> buttonsOptions;
    private VBox rootLayout;
    private final String[] OPTIONS_NAMES = {"Fullscreen: OFF", "Back"};

    public OptionsGUI() {
        initGraphics();
    }

    private void initGraphics() {
        rootLayout = new VBox(15);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");

        buttonsOptions = new ArrayList<>();

        for (String name : OPTIONS_NAMES) {
            Button button = new Button(name);
            button.setFont(Font.font("Consolas", 35));
            button.setMinWidth(400);
            button.setFocusTraversable(false);

            buttonsOptions.add(button);
            rootLayout.getChildren().add(button);
        }
    }

    public void updateSelection(int selectIndex) {
        for (int i = 0; i < buttonsOptions.size(); i++) {
            Button button = buttonsOptions.get(i);
            if (i == selectIndex) {
                String text = button.getText().replace(">", "").replace("<", "").trim();
                button.setText("> " + text + " <");
                button.setStyle("-fx-base: #b6e7b8;");
            } else {
                String text = button.getText().replace(">", "").replace("<", "").trim();
                button.setText(text);
                button.setStyle("");
            }
        }
    }

    public void updateFullscreenText(boolean isFull) {
        Button button = buttonsOptions.get(0);
        String label = isFull ? "Fullscreen: ON" : "Fullscreen: OFF";
        button.setText(label);
        updateSelection(0);
    }

    public VBox getLayout() {
        return rootLayout;
    }
}