package it.unibo.jrogue.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.engine.GameState;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Test relative for testing Menu logic.
 */
final class MenuTest {
    private MenuController menuController;
    private PauseGameController pauseController;

    @BeforeEach
    @SuppressWarnings("checkstyle:EmptyCatchBlock")
    void setUp() {
        try {
            Platform.startup(() -> { });
        } catch (final IllegalStateException ignored) {
            //JavaFX is already loaded so ignore it
        }
        final BaseController base = new BaseController(new GameState());
        menuController = new MenuController(base);
        pauseController = new PauseGameController(base);
    }

    @Test
    void testMenuLogic() throws NoSuchFieldException, IllegalAccessException {
        assertEquals(0, menuController.getCurrentIndex());
        simulateKeyPress(menuController, KeyCode.S);
        assertEquals(1, menuController.getCurrentIndex());
        simulateKeyPress(menuController, KeyCode.W);
        assertEquals(0, menuController.getCurrentIndex());
    }

    @Test
    void testMenuBounds() throws NoSuchFieldException, IllegalAccessException {
        simulateKeyPress(menuController, KeyCode.W);
        assertEquals(0, menuController.getCurrentIndex());
        for (int i = 0; i < 10; i++) {
            simulateKeyPress(menuController, KeyCode.S);
        }
        assertEquals(3, menuController.getCurrentIndex(), "Index must not be more of the available choices");
    }

    @Test
    void testPauseLogic() throws NoSuchFieldException, IllegalAccessException {
        simulateKeyPress(pauseController, KeyCode.S);
        simulateKeyPress(pauseController, KeyCode.S);
        assertEquals(2, pauseController.getCurrentIndex());
    }

    /**
     * Utility to simulate a KeyEvent.
     *
     * @param controller InputHandler which will receive the KeyEvent
     *
     * @param code the KeyEvent we simulate
     * */

    private void simulateKeyPress(final InputHandler controller, final KeyCode code) {
        final KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", code, false, false, false, false);
        controller.handleInput(event);
    }
}
