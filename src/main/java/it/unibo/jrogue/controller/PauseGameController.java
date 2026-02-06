package it.unibo.jrogue.controller;

import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import it.unibo.jrogue.boundary.PauseGameGUI;

/*Controller that handles the Pause menu when in game*/
public final class PauseGameController implements InputHandler {
    private final BaseController controller;
    private final PauseGameGUI pauseView;
    private final MenusNavigator pauseGameNav;
    private final MenusNavigator currentNavigator;

    /*Initializing the controller*/
    public PauseGameController(final BaseController controller) {
        this.controller = controller;
        this.pauseView = new PauseGameGUI();
        this.pauseGameNav = new MenusNavigator(3, pauseView::updateSelection);
        this.currentNavigator = this.pauseGameNav;
        updateGraphics();
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            currentNavigator.navigateUp();
        } else if (code == KeyCode.S) {
            currentNavigator.navigateDown();
        } else if (code == KeyCode.ENTER) {
            selectedChoice();
        }
    }
    /*Executing actions based on the selected index of the menu*/

    private void selectedChoice() {
        final int selection = currentNavigator.getSelection();
        if (currentNavigator.equals(pauseGameNav)) {
            switch (selection) {
                case 0:
                    //saveGame();
                    break;
                case 1:
                    controller.backToMainMenu();
                    break;
                case 2:
                    controller.resumeGame();
                    break;
                default:
                    break;
            }
        }
    }

    private void updateGraphics() {
        currentNavigator.update();
    }

    @Override
    public Pane getView() {
        return this.pauseView.getLayout();
    }
}
