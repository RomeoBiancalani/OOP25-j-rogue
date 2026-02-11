package it.unibo.jrogue.controller;

import it.unibo.jrogue.boundary.MainMenuGUI;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Controller that handles the Main menu and Options menu, it may change considering how hardcoded it is right now.
 * */
public final class MenuController implements InputHandler {
    private static final int BUTTONS_NUMBER = 4;
    private final BaseController controller;
    private final MainMenuGUI menuView;
    private int currentIndex;

    /**
     * Initialize the controller.
     *
     * @param controller which is the BaseController we communicate with
     */

    public MenuController(final BaseController controller) {
        this.controller = controller;
        this.menuView = new MainMenuGUI();
        this.currentIndex = 0;
        updateGraphics();
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            moveUp();
        } else if (code == KeyCode.S) {
            moveDown();
        } else if (code == KeyCode.ENTER) {
            selectedChoice();
        }
    }
    /**
     * Move upward inside the menu.
     * */

    private void moveUp() {
        if (currentIndex > 0) {
            currentIndex--;
            updateGraphics();
        }
    }
    /**
     * Move downward inside the menu.
     * */

    private void moveDown() {
        if (currentIndex < BUTTONS_NUMBER - 1) {
            currentIndex++;
            updateGraphics();
        }
    }

    /**
     * Execute the action based on the index of the menu.
     */

    private void selectedChoice() {
        switch (currentIndex) {
            case 0:
                controller.startGame();
                break;
            case 1:
                // loadGame();
                break;
            case 2:
                currentIndex = 0;
                updateGraphics();
                controller.goToOptions();
                break;
            case 3:
                javafx.application.Platform.exit();
                break;
            default:
                break;
        }
    }
    /**
     * Update the graphics based on index.
     * */

    private void updateGraphics() {
        menuView.updateSelection(currentIndex);
    }

    @Override
    public Pane getView() {
        return menuView.getLayout();
    }
}
