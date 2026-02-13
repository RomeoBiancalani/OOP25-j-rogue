package it.unibo.jrogue.controller;

import it.unibo.jrogue.boundary.VictoryGUI;
import it.unibo.jrogue.engine.BaseController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class VictoryController implements InputHandler {
    private final BaseController controller;
    private final VictoryGUI victory = new VictoryGUI();

    /**
     * This controller, handles the Victory screen of the game.
     * */
    public VictoryController(final BaseController controller){
        this.controller = controller;
    }

    @Override
    public void handleInput(final KeyEvent event){
        final KeyCode code = event.getCode();
        if (code == KeyCode.ENTER) {
            controller.backToMainMenu();
        }
    }

    @Override
    public Pane getView(){
        return victory.getLayout();
    }


}
