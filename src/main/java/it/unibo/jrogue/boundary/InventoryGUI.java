package it.unibo.jrogue.boundary;

import java.util.Optional;

import it.unibo.jrogue.controller.api.InventoryManager;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.items.impl.Armor;
import it.unibo.jrogue.entity.items.impl.MeleeWeapon;
import it.unibo.jrogue.entity.items.impl.Ring;
import it.unibo.jrogue.entity.items.impl.HealthPotion;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Inventory GUI.
 */
public class InventoryGUI {
    private StackPane[][] slotsMatrix;
    private InventoryManager manager;
    private GridPane gridPane;

    private final int cols = 10;
    private final int rows = 5;

    private int selectedRow = 0;
    private int selectedCol = 0;

    /**
     * The constructor for the SimpleGuiController.
     * 
     * @param manager the inventory manager linked to the player.
     */
    public InventoryGUI(final InventoryManager manager) {
        this.manager = manager;
        this.slotsMatrix = new StackPane[rows][cols];
        this.gridPane = new GridPane();

        initializeLayout();
        updateView();
    }

    /**
     * Helper method to get the graphical component of the invetory.
     * 
     * @return The GridPane containing the inventory slots.
     */
    public GridPane getView() {
        return this.gridPane;
    }

    public void handleInput(final KeyCode code) {
        if (code == KeyCode.S && selectedRow > 0) {
            selectedRow--;
        } else if (code == KeyCode.W && selectedRow < rows - 1) {
            selectedRow++;
        } else if (code == KeyCode.A && selectedCol > 0) {
            selectedCol--;
        } else if (code == KeyCode.D && selectedCol < cols - 1) {
            selectedCol++;
        } else if (code == KeyCode.ENTER) {
            final int index = (selectedRow * cols) + selectedCol;
            if (index < manager.getSize()) {
                manager.useItem(index);
            }
        }
        updateView();
    }

    /**
     * Initialize the grid layout.
     */
    private void initializeLayout() {
        gridPane.setStyle("-fx-background-color: #000000; -fx-padding: 20; -fx-alignment: center;");
        // gridPane.setGridLinesVisible(true);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                final StackPane slot = new StackPane();
                slotsMatrix[r][c] = slot;
                gridPane.add(slot, c, r);
            }
        }
    }

    /**
     * Method to make more clear on witch slot you are.
     */
    private void updateView() {

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                final StackPane slot = slotsMatrix[r][c];
                slot.getChildren().clear();

                final Rectangle bg = new Rectangle(60, 60);

                bg.setFill(Color.rgb(50, 50, 50));

                if (r == selectedRow && c == selectedCol) {
                    bg.setStroke(Color.RED);
                    bg.setStrokeWidth(3);
                } else {
                    bg.setStroke(Color.BLACK);
                    bg.setStrokeWidth(1);
                }
                slot.getChildren().add(bg);
                final int index = (r * cols) + c;
                if (index < manager.getSize()) {
                    final Optional<Item> result = manager.getItemAt(index);

                    if (result.isPresent()) {
                        final Item item = result.get();
                        final Text icon = new Text(getIconForItem(item));
                        icon.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                        slot.getChildren().add(icon);
                    }
                }
            }
        }
    }

    /**
     * Private Method that gives an icon for the item.
     * 
     * @param item the item in the inventory.
     * 
     * @return an icon representing the item.
     */
    private String getIconForItem(final Item item) {
        if (item instanceof MeleeWeapon) {
            MeleeWeapon weapon = (MeleeWeapon) item;
            String name = weapon.getName().toLowerCase();

            if (name.contains("ascia")) {
                return "ascia";
            } else if (name.contains("pugnale")) {
                return "pugnale";
            } else if (name.contains("spada")) {
                return "spada";
            }
            return "S";
        }

        if (item instanceof Armor) {
            return "Armor";
        }
        if (item instanceof Ring) {
            return "ring";
        }
        if (item instanceof HealthPotion) {
            return "pozione";
        }

        return "";
    }

}
