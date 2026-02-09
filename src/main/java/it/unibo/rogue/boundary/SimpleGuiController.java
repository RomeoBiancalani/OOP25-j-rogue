package it.unibo.rogue.boundary;

import java.util.Optional;

import it.unibo.rogue.control.api.InventoryManager;
import it.unibo.rogue.control.impl.InventoryManagerImpl;
import it.unibo.rogue.entity.items.api.Item;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Inventory GUI.
 */
public class SimpleGuiController extends Application {
    private StackPane[][] slotsMatrix;

    private int selectedRow = 0;
    private int selectedCol = 0;
    private InventoryManager manager;

    private final int cols = 10;
    private final int rows = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        this.manager = new InventoryManagerImpl(cols * rows);
        final GridPane gp = new GridPane();
        gp.setStyle("-fx-background-color: #000000; -fx-padding: 20; -fx-alignment: center;");
        gp.setGridLinesVisible(true);
        gp.setHgap(10);
        gp.setVgap(10);

        slotsMatrix = new StackPane[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                final StackPane slot = new StackPane();
                final Rectangle bg = new Rectangle(60, 60);
                bg.setFill(Color.ALICEBLUE);
                bg.setStroke(Color.BLACK);
                slot.getChildren().add(bg);

                slotsMatrix[r][c] = slot;
                gp.add(slot, c, r);
            }
        }
        final Scene scene = new Scene(gp);

        scene.setOnKeyPressed(event -> {
            final KeyCode code = event.getCode();

            if (code == KeyCode.W) {
                if (selectedRow > 0) {
                    selectedRow--;
                }
            } else if (code == KeyCode.S) {
                if (selectedRow < rows - 1) {
                    selectedRow++;
                }
            } else if (code == KeyCode.A) {
                if (selectedCol > 0) {
                    selectedCol--;
                }
            } else if (code == KeyCode.D) {
                if (selectedCol < cols - 1) {
                    selectedCol++;
                }
            } else if (code == KeyCode.ENTER) {
                final int index = (selectedRow * cols) + selectedCol;
                manager.useItem(index);
            }

            updateView();
        });

        updateView();
        stage.setTitle("Inventario");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
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

            if(name.contains("ascia")) {
                return "ascia";
            } else if (name.contains("pugnale")) {
                return "pugnale";
            } else if ( name.contains("spada")) {
                return "spada";
            }
            return "S";
        }

        if (item instanceof Armor) {
            return "A";
        }

        return "";
    }

    /**
     * Per ora la teniamo e basta.
     * 
     * @param args the roba.
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
