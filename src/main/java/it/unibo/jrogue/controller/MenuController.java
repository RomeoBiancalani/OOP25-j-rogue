package it.unibo.jrogue.controller;

import it.unibo.jrogue.boundary.MenuGUI;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.boundary.OptionsGUI;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/*Controller that handles the Main menu and Options menu, it may change considering how hardcoded it is right now*/
public final class MenuController implements InputHandler {
    private final BaseController controller;

    private final MenuGUI menuView;
    private final OptionsGUI optionsView;

    private final MenusNavigator mainMenuNav;
    private final MenusNavigator optionsNav;
    private MenusNavigator currentNavigator;

    /*Initialize the controller*/
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
    /*Execute the action based on the index of the menu*/

    private void selectedChoice() {
        final int selection = currentNavigator.getSelection();
        if (currentNavigator.equals(mainMenuNav)) {
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
                default:
                    break;
            }
        } else { /*If we are not in the menu we are in the options, it's a bit hardcoded, it could change if we realize
                    that we need more than 2 boundaries in this menu*/
            switch (selection) {
                case 0:
                    final boolean isFull = controller.toggleFullscreen();
                    optionsView.updateFullscreenText(isFull);
                    break;
                case 1:
                    backToMenu();
                    break;
                default:
                    break;
            }
        }
    }
    /*View for options*/

    private void goToOptions() {
        this.currentNavigator = optionsNav;
        controller.changeView(optionsView.getLayout());
        currentNavigator.update();
    }
    /*Set Controller and View back to the Menu*/

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
        if (currentNavigator.equals(optionsNav)) {
            return optionsView.getLayout();
        } else {
            return menuView.getLayout();
        }
    }
}
