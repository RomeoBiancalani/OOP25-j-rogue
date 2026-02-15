package it.unibo.jrogue.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jrogue.boundary.VictoryGUI;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * This class implements the victory screen after achieving victory in the game.
 * */

public final class VictoryController implements InputHandler {
    private final BaseController controller;
    private final GameController gameController;
    private final VictoryGUI victory = new VictoryGUI();

    /**
     * This controller, handles the Victory screen of the game.
     *
     * @param controller which is the BaseController we communicate with
     *
     * @param gameController needed to get the gold amount
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must be the same, i can't give a copy."
    )
    public VictoryController(final BaseController controller, final GameController gameController) {
        this.controller = controller;
        this.gameController = gameController;
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
     * Set the final score to be displayed on the GameOver screen.
     * */

    public void setScore() {
        final int score = gameController.getPlayer().getGold();
        this.victory.setScoreLabel(score);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getView() {
        setScore();
        return victory.getLayout();
    }

}
