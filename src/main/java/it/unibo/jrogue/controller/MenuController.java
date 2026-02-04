package it.unibo.jrogue.controller;

import it.unibo.jrogue.GUI.MenuGUI;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.GUI.PauseGameGUI;
import it.unibo.jrogue.GUI.OptionsGUI;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public final class MenuController implements InputHandler {

    private final BaseController controller;
    private final MenuGUI menuView;
    private final OptionsGUI optionsView;

    private final MenusNavigator mainMenuNav;
    private final MenusNavigator optionsNav;
    private MenusNavigator currentNavigator;

    public MenuController(final BaseController controller) {
        this.controller = controller;
        this.menuView = new MenuGUI();
        this.optionsView = new OptionsGUI();

        this.mainMenuNav = new MenusNavigator(4, menuView::updateSelection);
        this.optionsNav = new MenusNavigator(2, optionsView::updateSelection);
        this.currentNavigator = mainMenuNav;
        updateGraphics();
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            currentNavigator.navigateUp();   // Non serve più if/else!
        } else if (code == KeyCode.S) {
            currentNavigator.navigateDown(); // Non serve più if/else!
        } else if (code == KeyCode.ENTER) {
            selectedChoice();
        }
    }

    private void selectedChoice() {
        int selection = currentNavigator.getSelection();
        if (currentNavigator == mainMenuNav) {
            switch (selection) {
                case 0:
                    controller.startGame();
                    break;
                case 1:
                    // loadGame();
                    break;
                case 2:
                    goToOptions();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        } else { /*If we are not in the menu we are in the options, it's a bit hardcoded, it could change if we realize
                    that we need more than 2 boundaries*/
            switch (selection) {
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
        this.currentNavigator = optionsNav;
        controller.changeView(optionsView.getLayout());
        currentNavigator.update();
    }

    private void backToMenu() {
        this.currentNavigator = mainMenuNav;
        controller.changeView(menuView.getLayout());
        currentNavigator.update();
    }

    private void updateGraphics() {
        currentNavigator.update();
    }

    @Override
    public Pane getView() {
        if (currentNavigator == optionsNav) {
            return optionsView.getLayout();
        } else {
            return menuView.getLayout();
        }
    }
}