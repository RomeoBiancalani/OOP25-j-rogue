package it.unibo.jrogue.boundary;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

/*Boundary for the MainMenu*/
public final class MenuGUI {
    private static final String[] BUTTONS_NAME = {"New Game", "Load Game", "Options", "Exit"};
    private final List<Button> buttonsMenu;
    private final VBox rootLayout;

    public MenuGUI() {
        rootLayout = new VBox(10);
        buttonsMenu = new ArrayList<>();
        initGraphics();
    }

    /*Configure the elements of the Menu, this method can be used also for the other menus*/
    public void initGraphics() {
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgb(0,0,0);");
        for (final String name : BUTTONS_NAME) {
            final Button button = new Button(name);
            button.setFont(Font.font("Consolas", 35));
            button.setMinWidth(400);
            button.setFocusTraversable(false);
            buttonsMenu.add(button);
            rootLayout.getChildren().add(button);
        }
    }
    /*Update the view of the buttons based on the selected button*/

    public void updateSelection(final int selectIndex) {
        for (int i = 0; i < buttonsMenu.size(); i++) {
            final Button button = buttonsMenu.get(i);
            if (i == selectIndex) {
                button.setText("> " + BUTTONS_NAME[i] + " <");
                button.setStyle("-fx-base: #b6e7b8;");
            } else {
                button.setText(BUTTONS_NAME[i]);
                button.setStyle("");
            }
        }
    }
    /*return the layout of the menu*/

    public VBox getLayout() {
        return rootLayout;
    }
}
