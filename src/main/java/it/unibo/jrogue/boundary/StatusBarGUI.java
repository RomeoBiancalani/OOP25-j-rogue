package it.unibo.jrogue.boundary;

import java.util.Objects;

import it.unibo.jrogue.entity.entities.api.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class rapresents the graphical HUD positioned at the bottom of the game
 * screen.
 * 
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
    private static final int BAR_WIDTH = 200;
    private static final int XP_TO_LEVEL_UP = 20;

    private final Label hpLabel = new Label();
    private final Label levelLabel = new Label();
    private final Label xpLabel = new Label();
    private final Label goldLabel = new Label();

    private final ProgressBar hpBar = new ProgressBar(1.0);
    private final ProgressBar xpBar = new ProgressBar(0.0);
    private final StackPane hpStack = new StackPane(hpBar, hpLabel);
    private final StackPane xpStack = new StackPane(xpBar, xpLabel);

    /**
     * Constructs a new StatusBar with the default styling.
     */
    public StatusBarGUI() {
        this.setSpacing(SPACING);
        this.setPadding(new Insets(PADDING));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, " + OPACITY + ");");
        this.setMaxHeight(MAX_HEIGHT);

        styleLabel(hpLabel, Color.BLACK);
        styleLabel(xpLabel, Color.BLACK);
        styleLabel(goldLabel, Color.GOLD);
        styleLabel(levelLabel, Color.WHITE);

        styleBar(hpBar, "red");
        styleBar(xpBar, "deepskyblue");
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
     * Applies a uniform style to the statistic bar.
     * 
     * @param bar The bar to be styled.
     * @param cssColor The css xcolor to apply to the text
     */
    private void styleBar(final ProgressBar bar, final String cssColor) {
        bar.setPrefWidth(BAR_WIDTH);
        bar.setStyle("-fx-accent: " + cssColor + ";");
    }

    /**
     * Updates the text of the label based on the current state of the player.
     * 
     * @param player The player whose statistics will be displayed.
     * @throws NullPointerExceptions if the provided player is null.
     */
    public void update(final Player player) {
        Objects.requireNonNull(player, "Player must be not null");

        goldLabel.setText("Gold: " + player.getGold());
        levelLabel.setText("Level: " + player.getLevel());

        final double hpPercent = (double) player.getLifePoint() / player.getMaxLifePoint();
        hpBar.setProgress(hpPercent);
        hpLabel.setText("HP: " + player.getLifePoint() + "/" + player.getMaxLifePoint());

        final double xpPercent = (double) player.getXP() / XP_TO_LEVEL_UP;
        xpBar.setProgress(xpPercent);
        xpLabel.setText("XP: " + player.getXP() + "/20");        

        this.getChildren().clear();
        this.getChildren().addAll(hpStack, xpStack, goldLabel, levelLabel);
    }
}
