package it.unibo.jrogue.controller;

import it.unibo.jrogue.boundary.GameOverGUI;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Controller that manages the Game Over situation.
 */
public class GameOverController implements InputHandler {
    private final BaseController controller;
    private final GameController gameController;
    private final GameOverGUI gameOver = new GameOverGUI();

    /**
     * This controller, handles the GameOver screen of the game.
     * @param controller
     * @param gameController
     */
    public GameOverController(final BaseController controller, final GameController gameController) {
        this.controller = controller;
        this.gameController = gameController;
    }

    public void setScore() {
        final int score = gameController.getPlayer().getGold();
        this.gameOver.setScoreLabel(score);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInput(final KeyEvent event) {
        final KeyCode code = event.getCode();
        if (code == KeyCode.ENTER) {
            controller.backToMainMenu();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getView() {
        setScore();
        return gameOver.getLayout();
    }

}
