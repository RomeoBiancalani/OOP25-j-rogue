package it.unibo.jrogue.controller;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;

/**
 * Controller for the inventory*/

public final class InventoryController implements InputHandler {
    private final BaseController controller;
    private final Pane inventoryView;

    /**
     * Initialize the controller
     *
     * @param controller which is the BaseController we communicate with*/

    public InventoryController(final BaseController controller) {
        this.controller = controller;
        this.inventoryView = new Pane(); //Temporary Pane waiting for the team to make Inventory GUI
        this.inventoryView.setStyle("-fx-background-color: #c800ff;");

    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            //moveUp();
        } else if (code == KeyCode.A) {
            //moveLeft();
        } else if (code == KeyCode.D) {
            //moveRight();
        } else if (code == KeyCode.S) {
            //moveDown();
        } else if (code == KeyCode.Q) {
            controller.resumeGame();
        } else if (code == KeyCode.ENTER) {
            //selectItem();
        } else if (code == KeyCode.F) {
            //readItem();
        }
    }

    @Override
    public Pane getView() {
        return this.inventoryView;
    }
}
