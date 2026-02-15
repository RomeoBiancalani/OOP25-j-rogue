package it.unibo.jrogue.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import it.unibo.jrogue.boundary.PauseGameGUI;

/**
 * Controller that handles the Pause menu when in game.
 * */

public final class PauseGameController implements InputHandler {
    private static final int BUTTONS_NUMBER = 4;
    private final BaseController controller;
    private final PauseGameGUI pauseView;
    private int currentIndex;

    /**
     * Initialize the controller.
     *
     * @param controller which is the BaseController we communicate with
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must be the same, i can't give a copy."
    )
    public PauseGameController(final BaseController controller) {
        this.controller = controller;
        this.pauseView = new PauseGameGUI();
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
        } else if (code == KeyCode.ESCAPE) {
            currentIndex = 0;
            updateGraphics();
            controller.resumeGame();
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
     * Executing actions based on the selected index of the menu.
     */

    private void selectedChoice() {
        switch (currentIndex) {
            case 0:
                controller.saveGame();
                break;
            case 1:
                currentIndex = 0;
                updateGraphics();
                controller.goToOptions();
                break;
            case 2:
                currentIndex = 0;
                updateGraphics();
                controller.backToMainMenu();
                break;
            case 3:
                currentIndex = 0;
                updateGraphics();
                controller.resumeGame();
                break;
            default:
                break;
        }
    }
    /**
     * Update the graphics based on index.
     * */

    private void updateGraphics() {
        pauseView.updateSelection(currentIndex);
    }

    @Override
    public Pane getView() {
        return this.pauseView.getLayout();
    }

    /**
     * Getter of the index to use in MenuTest.
     *
     * @return currentIndex
     * */

    public int getCurrentIndex() {
        return this.currentIndex;
    }
}
