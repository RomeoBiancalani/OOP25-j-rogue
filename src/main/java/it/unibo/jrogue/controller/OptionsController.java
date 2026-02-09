package it.unibo.jrogue.controller;

import it.unibo.jrogue.boundary.OptionsGUI;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Controller that handles the options menu.
 */
public final class OptionsController implements InputHandler {

    private static final int OPTIONS_ITEMS = 2;
    private final BaseController controller;
    private final OptionsGUI optionsView;
    private int currentIndex;

    /**
     * Initialize the Options controller.
     *
     * @param controller which is the BaseController
     */
    public OptionsController(final BaseController controller) {
        this.controller = controller;
        this.optionsView = new OptionsGUI();
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
        if (currentIndex < OPTIONS_ITEMS - 1) {
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
                final boolean isFull = controller.toggleFullscreen();
                optionsView.updateFullscreenText(isFull);
                break;
            case 1:
                currentIndex = 0;
                updateGraphics();
                controller.goBack();
                break;
            default:
                break;
        }
    }
    /**
     * Update the graphics based on index.
     * */

    private void updateGraphics() {
        optionsView.updateSelection(currentIndex);
    }

    @Override
    public Pane getView() {
        return optionsView.getLayout();
    }
}
