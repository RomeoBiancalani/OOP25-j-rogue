package it.unibo.jrogue.controller;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.entity.entities.api.Player;

import java.util.Map;

import it.unibo.jrogue.boundary.InventoryGUI;
import it.unibo.jrogue.controller.api.InventoryManager;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * Controller for the inventory.
 */

public final class InventoryController implements InputHandler {
    private final BaseController controller;
    private InventoryGUI inventoryGUI;
    private InventoryManager manager;

    /**
     * Initialize the controller.
     *
     * @param controller which is the BaseController we communicate with.
     */

    public InventoryController(final BaseController controller) {
        this.controller = controller;
    }

    /**
     * Initializes the player used then for the manager.
     * 
     * @param player the player needed.
     * 
     * @param sprites a map with all the sprites saved.
     */
    public void setupPlayer(final Player player, final Map<String, Image> sprites) {
        this.manager = new InventoryManagerImpl(player);
        this.inventoryGUI = new InventoryGUI(manager, sprites);
    }

    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();

        if (code == KeyCode.Q || code == KeyCode.ESCAPE || code == KeyCode.I) {
            controller.resumeGame();
        } else {
            inventoryGUI.handleInput(code);
        }
    }

    @Override
    public Pane getView() {
        if (this.inventoryGUI == null) {
            return new Pane();
        }
        this.inventoryGUI.updateView();
        return this.inventoryGUI.getView();
    }
}
