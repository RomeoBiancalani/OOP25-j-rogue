package it.unibo.Jrogue.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

public class MenuGUI {

    private List<Button> buttonsMenu;
    private VBox rootLayout;

    private final String[] BUTTONS_NAME = {"Play", "Options", "Exit"};

    public MenuGUI() {
        initGraphics();
    }

    private void initGraphics() {
        rootLayout = new VBox(10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");

        buttonsMenu = new ArrayList<>();

        for (String name : BUTTONS_NAME) {
            Button button = new Button(name);
            button.setFont(Font.font("Consolas", 35));
            button.setMinWidth(400);
            button.setFocusTraversable(false);

            buttonsMenu.add(button);
            rootLayout.getChildren().add(button);
        }
    }

    public void updateSelection(int selectIndex) {
        for (int i = 0; i < buttonsMenu.size(); i++) {
            Button button = buttonsMenu.get(i);
            if (i == selectIndex) {
                button.setText("> " + BUTTONS_NAME[i] + " <");
                button.setStyle("-fx-base: #b6e7b8;");
            } else {
                button.setText(BUTTONS_NAME[i]);
                button.setStyle("");
            }
        }
    }
    public VBox getLayout() {
        return rootLayout;
    }
}