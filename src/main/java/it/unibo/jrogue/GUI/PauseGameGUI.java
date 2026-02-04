package it.unibo.jrogue.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

public class PauseGameGUI {

    private List<Button> buttonsPause;
    private VBox rootLayout;

    private final String[] BUTTONS_NAME = {"Save Game", "Back to Menu", "Return"};

    public PauseGameGUI() {
        initGraphics();
    }

    private void initGraphics() {
        rootLayout = new VBox(10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");

        buttonsPause = new ArrayList<>();

        for (String name : BUTTONS_NAME) {
            Button button = new Button(name);
            button.setFont(Font.font("Consolas", 35));
            button.setMinWidth(400);
            button.setFocusTraversable(false);

            buttonsPause.add(button);
            rootLayout.getChildren().add(button);
        }
    }

    public void updateSelection(int selectIndex) {
        for (int i = 0; i < buttonsPause.size(); i++) {
            Button button = buttonsPause.get(i);
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

    public VBox getLayout() {
        return rootLayout;
    }
}
