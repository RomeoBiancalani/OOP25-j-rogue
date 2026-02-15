package it.unibo.jrogue.boundary;

import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * A graphical component that displays game events and feedback to the player.
 */
public class MessageDialog extends HBox {

    private static final int PADDING = 20;
    private static final int MAX_HEIGHT = 50;
    private static final double OPACITY = 0.7;
    private static final int FONT_SIZE = 20;
    private static final int QUEUE_MAX_SIZE = 4;

    private final Label messagLabel = new Label();
    private final SequentialTransition transition;
    private final FadeTransition fadeOut;
    private final Queue<String> queue = new LinkedList<>();

    /**
     * Constructs a new status bar with the default styling.
     */
    public MessageDialog() {
        this.setPadding(new Insets(PADDING));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, " + OPACITY + ");");
        this.setMaxHeight(MAX_HEIGHT);

        messagLabel.setTextFill(Color.WHITE);
        messagLabel.setFont(Font.font("Consolas", FontWeight.BOLD, FONT_SIZE));

        final FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), messagLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        final PauseTransition stayVisible = new PauseTransition(Duration.seconds(1));

        fadeOut = new FadeTransition(Duration.seconds(0.5), messagLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        transition = new SequentialTransition(fadeIn, stayVisible, fadeOut);

        transition.setOnFinished(e -> {
            if (!queue.isEmpty()) {
                showNextFromQueue();
            }
        });
    }

    /**
     * Sets and display a new message in the dialog.
     * 
     * @param message The message to be displayed.
     */
    public void setMessage(final String message) {
        this.getChildren().clear();
        this.getChildren().add(messagLabel);
        if (transition.getStatus() == Animation.Status.RUNNING) {
            queue.add(message);
        } else {
            messagLabel.setText(message);
            transition.playFromStart();
        }
    }

    /**
     * Show the next message in the queue message.
     */
    private void showNextFromQueue() {
        if (queue.size() >= QUEUE_MAX_SIZE) {
            String lastMessage = queue.poll();
            while (!queue.isEmpty()) {
                lastMessage = queue.poll();
            }
            messagLabel.setText(lastMessage);
        } else {
            messagLabel.setText(queue.poll());
        }
        transition.playFromStart();
    }
}
