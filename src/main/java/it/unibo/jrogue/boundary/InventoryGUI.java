package it.unibo.jrogue.boundary;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jrogue.controller.api.InventoryManager;
import it.unibo.jrogue.entity.items.api.Equipment;
import it.unibo.jrogue.entity.items.api.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public static final int GRID_COLS = 10;
    public static final int GRID_ROWS = 5;

    private static final int GRID_GAP = 5;
    private static final Color COLOR_SELECTED = Color.RED;
    private static final Color COLOR_NORMAL = Color.BLACK;
    private static final Color COLOR_EQUIPPED = Color.BLUE;
    private static final Color COLOR_SLOT_BG = Color.LIGHTGRAY;
    private static final Color COLOR_TEXT = Color.WHITE;

    private static final int SLOT_SIZE = 50;
    private static final int ICON_SIZE = 40;

    private static final int STROKE_NORMAL = 1;
    private static final int STROKE_EQUIPPED = 3;
    private static final int STROKE_SELECTED = 4;

    private static final int LAYOUT_SPACING = 20;
    private static final int LAYOUT_PADDING = 20;
    private static final int FONT_SIZE_DESC = 18;
    private static final int WRAP_WIDTH_DESC = 500;

    private final StackPane[][] slotsMatrix;
    private final InventoryManager manager;

    private final VBox mainLayout;
    private final GridPane gridPane;
    private final Text description;

    private final Map<String, Image> sprites;

    /**
     * The constructor for the InventoryGUI.
     * 
     * @param manager the inventory manager linked to the player.
     * 
     * @param sprites the map with all the sprites saved.
     */
    public InventoryGUI(final InventoryManager manager, final Map<String, Image> sprites) {
        this.sprites = new HashMap<>(sprites);
        this.manager = manager;
        this.slotsMatrix = new StackPane[GRID_ROWS][GRID_COLS];

        this.mainLayout = new VBox(LAYOUT_SPACING);
        this.mainLayout.setAlignment(Pos.CENTER);
        this.mainLayout.setPadding(new Insets(LAYOUT_PADDING));

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(GRID_GAP);
        gridPane.setVgap(GRID_GAP);

        this.description = new Text("Empty inventory");
        this.description.setFill(COLOR_TEXT);
        this.description.setFont(Font.font("Verdana", FONT_SIZE_DESC));
        this.description.setWrappingWidth(WRAP_WIDTH_DESC);
        this.description.setTextAlignment(TextAlignment.CENTER);

        initializeLayout();

        this.mainLayout.getChildren().addAll(gridPane, description);
        updateView(0, 0);
    }

    /**
     * Initialize the grid layout.
     */
    private void initializeLayout() {
        gridPane.setGridLinesVisible(true);

        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < GRID_COLS; c++) {
                final StackPane slot = new StackPane();
                final Rectangle bg = new Rectangle(SLOT_SIZE, SLOT_SIZE);
                bg.setFill(COLOR_SLOT_BG);
                bg.setStroke(COLOR_NORMAL);

                slot.getChildren().add(bg);
                gridPane.add(slot, c, r);
                slotsMatrix[r][c] = slot;

            }
        }
    }

    /**
     * Refreshes the inventory view.
     * 
     * @param selectedRow the row currently on.
     * 
     * @param selectedCol the col currently on.
     */
    public final void updateView(final int selectedRow, final int selectedCol) {

        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < GRID_COLS; c++) {
                final StackPane slot = slotsMatrix[r][c];

                if (slot.getChildren().size() > 1) {
                    slot.getChildren().remove(1, slot.getChildren().size());
                }
                final Rectangle bg = (Rectangle) slot.getChildren().get(0);

                bg.setStroke(COLOR_NORMAL);
                bg.setStrokeWidth(STROKE_NORMAL);

                final int index = (r * GRID_COLS) + c;
                final boolean isSelected = r == selectedRow && c == selectedCol;
                final boolean isEquipped = manager.isEquipped(index);
                Optional<Item> itemInSlot = Optional.empty();
                if (index < manager.getSize()) {
                    itemInSlot = manager.getItemAt(index);
                }

                if (isSelected) {
                    bg.setStroke(COLOR_SELECTED);
                    bg.setStrokeWidth(STROKE_SELECTED);
                    slot.toFront();
                } else if (isEquipped) {
                    bg.setStroke(COLOR_EQUIPPED);
                    bg.setStrokeWidth(STROKE_EQUIPPED);
                } else {
                    bg.setStroke(COLOR_NORMAL);
                    bg.setStrokeWidth(STROKE_NORMAL);
                }
                if (itemInSlot.isPresent()) {
                    final Item item = itemInSlot.get();

                    final String spriteName = DungeonRenderer.getItemSprite(item);
                    final Image img = sprites.get(spriteName);

                    if (img != null) {
                        final ImageView view = new ImageView(img);
                        view.setFitWidth(ICON_SIZE);
                        view.setFitHeight(ICON_SIZE);
                        slot.getChildren().add(view);
                    }

                    if (item instanceof Equipment && isEquipped) {

                        final Label badge = new Label("E");
                        badge.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
                        badge.setTextFill(Color.WHITE);
                        badge.setStyle("-fx-background-color: blue; -fx-padding: 2; -fx-background-radius: 3;");
                        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
                        slot.getChildren().add(badge);

                    }
                }
                if (isSelected) {
                    if (itemInSlot.isPresent()) {
                        updateDescription(itemInSlot.get());
                    } else {
                        description.setText("slot vuoto");
                    }
                }

            }
        }
    }

    /**
     * Updates the description text area based on witch item we are upon.
     * 
     * @param item the item currently selected.
     * 
     */
    private void updateDescription(final Item item) {
        final StringBuilder sb = new StringBuilder();

        sb.append(item.getDescription());
        description.setText(sb.toString());
    }

    /**
     * Helper method to get the graphical component of the invetory.
     * 
     * @return The VBox containing the inventory grid and the description area.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Returning the internal VBox is necessary for JavaFX to render the component in the Scene Graph."
    )
    public VBox getView() {
        return this.mainLayout;
    }

}
