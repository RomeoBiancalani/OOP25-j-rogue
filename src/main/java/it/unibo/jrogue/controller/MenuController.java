package it.unibo.Jrogue.controller;

import it.unibo.Jrogue.engine.BaseController;
import it.unibo.Jrogue.GUI.MenuGUI;
import it.unibo.Jrogue.GUI.OptionsGUI;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public final class MenuController implements InputHandler {

    private final BaseController controller;
    private final MenuGUI menuView;
    private final OptionsGUI optionsView;

    private boolean inOptions;
    private int selectedMenuButton ;
    private int selectedOptionsButton;

    private final int menuButtonNumber = 3;
    private final int optionsButtonNumber = 2;

    public MenuController(final BaseController controller) {
        this.controller = controller;
        this.menuView = new MenuGUI();
        this.optionsView = new OptionsGUI();
        updateGraphics();
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            navigateUp();
        } else if (code == KeyCode.S) {
            navigateDown();
        } else if (code == KeyCode.ENTER) {
            confirmChoice();
        }
    }
    /*We check if we are in Options first, the code may be redundant and unpleasant to look at so it may be fixed*/

    private void navigateUp() {
        if (!inOptions) {
            if (selectedMenuButton > 0) {
                selectedMenuButton--;
                menuView.updateSelection(selectedMenuButton);
            }
        } else {
            if (selectedOptionsButton > 0) {
                selectedOptionsButton--;
                optionsView.updateSelection(selectedOptionsButton);
            }
        }
    }

    private void navigateDown() {
        if (!inOptions) {
            if (selectedMenuButton < menuButtonNumber - 1) {
                selectedMenuButton++;
                menuView.updateSelection(selectedMenuButton);
            }
        } else {
            if (selectedOptionsButton < optionsButtonNumber - 1) {
                selectedOptionsButton++;
                optionsView.updateSelection(selectedOptionsButton);
            }
        }
    }

    private void confirmChoice() {
        if (!inOptions) {
            switch (selectedMenuButton) {
                case 0:
                    controller.startGame();
                    break;
                case 1:
                    goToOptions();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        } else {
            switch (selectedOptionsButton) {
                case 0:
                    final boolean isFull = controller.toggleFullscreen();
                    optionsView.updateFullscreenText(isFull);
                    break;
                case 1:
                    backToMenu();
                    break;
            }
        }
    }

    private void goToOptions() {
        inOptions = true;
        controller.changeView(optionsView.getLayout());
        optionsView.updateSelection(selectedOptionsButton);
    }

    private void backToMenu() {
        inOptions = false;
        controller.changeView(menuView.getLayout());
        menuView.updateSelection(selectedMenuButton);
    }

    private void updateGraphics() {
        menuView.updateSelection(selectedMenuButton);
        optionsView.updateSelection(selectedOptionsButton);
    }
    @Override
    public Pane getView() {
        return menuView.getLayout();
    }
}
