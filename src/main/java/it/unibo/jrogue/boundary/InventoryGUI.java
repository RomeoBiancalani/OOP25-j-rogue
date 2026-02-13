package it.unibo.jrogue.boundary;

import java.util.Map;
import java.util.Optional;

import it.unibo.jrogue.controller.api.InventoryManager;
import it.unibo.jrogue.entity.items.api.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Class responsible for the GUI of the Player's Inventory.
 */
public class InventoryGUI {
    private static final Color COLOR_SELECTED = Color.RED;
    private static final Color COLOR_NORMAL = Color.BLACK;
    private static final Color COLOR_EQUIPPED = Color.BLUE;
    private static final int VBOX_SPACING = 20;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;

    private StackPane[][] slotsMatrix;
    private InventoryManager manager;

    private VBox mainLayout;
    private GridPane gridPane;
    private Text description;

    private final int cols = 10;
    private final int rows = 5;

    private int selectedRow;
    private int selectedCol;

    private final Map<String, Image> sprites;

    /**
     * The constructor for the InventoryGUI.
     * 
     * @param manager the inventory manager linked to the player.
     */
    public InventoryGUI(final InventoryManager manager, final Map<String, Image> sprites) {
        this.sprites = sprites;
        this.manager = manager;
        this.slotsMatrix = new StackPane[rows][cols];

        this.mainLayout = new VBox(VBOX_SPACING);
        this.mainLayout.setAlignment(Pos.CENTER);
        this.mainLayout.setPadding(new Insets(20));

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        this.description = new Text("inventario vuoto");
        this.description.setFill(Color.WHITE);
        this.description.setFont(Font.font("Verdana", 18));
        this.description.setWrappingWidth(500);
        this.description.setTextAlignment(TextAlignment.CENTER);

        initializeLayout();

        this.mainLayout.getChildren().addAll(gridPane, description);
        updateView();
    }

    /**
     * Initialize the grid layout.
     */
    private void initializeLayout() {
        /*
         * gridPane.setStyle("-fx-background-color: #000000; -fx-padding: 20;
         * -fx-alignment: center;");
         */
        gridPane.setGridLinesVisible(true);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                final StackPane slot = new StackPane();
                final Rectangle bg = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                bg.setFill(Color.LIGHTGRAY);
                bg.setStroke(COLOR_NORMAL);

                slot.getChildren().add(bg);
                gridPane.add(slot, c, r);
                slotsMatrix[r][c] = slot;

            }
        }
    }

    /**
     * Refreshes the inventory view.
     */
    public void updateView() {

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                final StackPane slot = slotsMatrix[r][c];

                if (slot.getChildren().size() > 1) {
                    slot.getChildren().remove(1, slot.getChildren().size());
                }
                final Rectangle bg = (Rectangle) slot.getChildren().get(0);

                if (r == selectedRow && c == selectedCol) {
                    bg.setStroke(COLOR_SELECTED);
                    bg.setStrokeWidth(3);
                } else {
                    bg.setStroke(COLOR_NORMAL);
                    bg.setStrokeWidth(1);
                }

                final int index = (r * cols) + c;
                final boolean isSelected = r == selectedRow && c == selectedCol;
                final boolean isEquipped = manager.isEquipped(index);

                if (isSelected) {
                    bg.setStroke(COLOR_SELECTED);
                    bg.setStrokeWidth(4);

                    slot.toFront();
                } else if (isEquipped) {
                    bg.setStroke(COLOR_EQUIPPED);
                    bg.setStrokeWidth(3);
                } else {
                    bg.setStroke(COLOR_NORMAL);
                    bg.setStrokeWidth(1);
                }
                if (index < manager.getSize()) {
                    final Optional<Item> result = manager.getItemAt(index);

                    if (result.isPresent()) {
                        final Item item = result.get();

                        final String spriteName = DungeonRenderer.getItemSprite(item);
                        final Image img = sprites.get(spriteName);

                        if (img != null) {
                            final ImageView view = new ImageView(img);
                            view.setFitWidth(40);
                            view.setFitHeight(40);
                            slot.getChildren().add(view);
                        }

                        if (isEquipped) {
                            final Label badge = new Label("E");
                            badge.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
                            badge.setTextFill(Color.WHITE);
                            badge.setStyle("-fx-background-color: blue; -fx-padding: 2; -fx-background-radius: 3;");
                            StackPane.setAlignment(badge, Pos.TOP_RIGHT); // Angolo in alto a destra
                            slot.getChildren().add(badge);
                        }

                        if (isSelected) {
                            updateDescription(item, isEquipped);
                        }

                    }

                } else if (isSelected) {
                    description.setText("slot vuoto");
                }
            }
        }
    }

    /**
     * Updates the description text area based on witch item we are upon.
     * 
     * @param item       the item currently selected.
     * 
     * @param isEquipped true if the item is currently equipped.
     */
    private void updateDescription(final Item item, final boolean isEquipped) {
        final StringBuilder sb = new StringBuilder();

        sb.append(item.getDescription());
        description.setText(sb.toString());
    }

    /**
     * Helper method to get the graphical component of the invetory.
     * 
     * @return The VBox containing the inventory grid and the description area.
     */
    public VBox getView() {
        return this.mainLayout;
    }

    /**
     * Handles keyboard input for navigation and interaction.
     * 
     * @param code the KeyCode of the key pressed.
     */
    public void handleInput(final KeyCode code) {
        if (code == KeyCode.W && selectedRow > 0) {
            selectedRow--;
        } else if (code == KeyCode.S && selectedRow < rows - 1) {
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

}
