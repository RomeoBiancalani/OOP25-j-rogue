package it.unibo.jrogue.controller;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.entity.entities.api.Player;

import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

    private int selectedRow;
    private int selectedCol;

    /**
     * Initialize the controller.
     *
     * @param controller which is the BaseController we communicate with.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", 
    justification = "InventoryController needs a reference to the main BaseController to switch scenes.")
    public InventoryController(final BaseController controller) {
        this.controller = controller;
    }

    /**
     * Initializes the player used then for the manager.
     * 
     * @param player  the player needed.
     * 
     * @param sprites a map with all the sprites saved.
     */
    public void setupPlayer(final Player player, final Map<String, Image> sprites) {
        this.manager = new InventoryManagerImpl(player);
        this.inventoryGUI = new InventoryGUI(manager, sprites);

        this.selectedRow = 0;
        this.selectedCol = 0;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void handleInput(final KeyEvent event) {
        if (this.manager == null || this.inventoryGUI == null) {
            return;
        }
        final KeyCode code = event.getCode();
        if (code == KeyCode.W && selectedRow > 0) {
            selectedRow--;
        } else if (code == KeyCode.S && selectedRow < InventoryGUI.GRID_ROWS - 1) {
            selectedRow++;
        } else if (code == KeyCode.A && selectedCol > 0) {
            selectedCol--;
        } else if (code == KeyCode.D && selectedCol < InventoryGUI.GRID_COLS - 1) {
            selectedCol++;
        } else if (code == KeyCode.E) {
            final int index = (selectedRow * InventoryGUI.GRID_COLS) + selectedCol;
            if (index < manager.getSize()) {
                manager.useItem(index);
            }
        } else if (code == KeyCode.R) {
            final int index = (selectedRow * InventoryGUI.GRID_COLS) + selectedCol;
            if (index < manager.getSize()) {
                manager.dropItem(index);
            }
        } else if (code == KeyCode.Q || code == KeyCode.ESCAPE) {
            controller.resumeGame();
        }

        inventoryGUI.updateView(selectedRow, selectedCol);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getView() {
        if (this.inventoryGUI == null) {
            return new Pane();
        }
        inventoryGUI.updateView(selectedRow, selectedCol);
        return this.inventoryGUI.getView();
    }
}
