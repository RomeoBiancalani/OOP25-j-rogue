package it.unibo.jrogue.boundary;

import java.util.Objects;

import it.unibo.jrogue.entity.entities.api.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class rapresents the graphical HUD positioned at the bottom of the game
 * screen.
 * <p>
 * It provides real-time visualization of the player's statistics
 * </p>
 */
public class StatusBarGUI extends HBox {
    private static final int SPACING = 20;
    private static final int PADDING = 10;
    private static final int MAX_HEIGHT = 50;
    private static final double OPACITY = 0.7;
    private static final int FONT_SIZE = 20;

    private final Label hpLabel = new Label();
    private final Label levelLabel = new Label();
    private final Label xpLabel = new Label();
    private final Label goldLabel = new Label();

    /**
     * Constructs a new StatusBar with the default styling.
     */
    public StatusBarGUI() {
        this.setSpacing(SPACING);
        this.setPadding(new Insets(PADDING));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, " + OPACITY + ");");
        this.setMaxHeight(MAX_HEIGHT);

        styleLabel(hpLabel, Color.RED);
        styleLabel(xpLabel, Color.LIGHTBLUE);
        styleLabel(goldLabel, Color.GOLD);
        styleLabel(levelLabel, Color.WHITE);

        this.getChildren().addAll(hpLabel, xpLabel, goldLabel, levelLabel);
    }

    /**
     * Applies a uniform style to the statistic labels.
     * 
     * @param label The label to be styled.
     * @param color The color to apply to the text
     */
    private void styleLabel(final Label label, final Color color) {
        label.setTextFill(color);
        label.setFont(Font.font("Consolas", FontWeight.BOLD, FONT_SIZE));
    }

    /**
     * Updates the text of the label based on the current state of the player.
     * 
     * @param player
     * @throws NullPointerExceptions if the provided player is null.
     */
    public void update(final Player player) {
        Objects.requireNonNull(player, "Player must be not null");
        hpLabel.setText("HP: " + player.getLifePoint() + "/" + player.getMaxLifePoint());
        xpLabel.setText("XP: " + player.getXP() + "/20");
        goldLabel.setText("Gold: " + player.getGold());
        levelLabel.setText("Level: " + player.getLevel());
    }
}
