package it.unibo.jrogue.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import it.unibo.jrogue.engine.BaseController;
import it.unibo.jrogue.engine.GameState;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Test relative for testing Menu logic.
 */
public final class MenuTest {
    private MenuController menuController;
    private PauseGameController pauseController;

    @BeforeEach
    void setUp() {
        try {
            Platform.startup(() -> { });
        } catch (final IllegalStateException ignored) { }
        final BaseController base = new BaseController(new GameState());
        menuController = new MenuController(base);
        pauseController = new PauseGameController(base);
    }

    @Test
    void testMenuLogic() throws Exception {
        assertEquals(0, getIndex(menuController));
        simulateKeyPress(menuController, KeyCode.S);
        assertEquals(1, getIndex(menuController));
        simulateKeyPress(menuController, KeyCode.W);
        assertEquals(0, getIndex(menuController));
    }

    @Test
    void testMenuBounds() throws Exception {
        simulateKeyPress(menuController, KeyCode.W);
        assertEquals(0, getIndex(menuController));
        for (int i = 0; i < 10; i++) {
            simulateKeyPress(menuController, KeyCode.S);
        }
        assertEquals(3, getIndex(menuController), "Index must not be more of the available choices");
    }

    @Test
    void testPauseLogic() throws Exception {
        simulateKeyPress(pauseController, KeyCode.S);
        simulateKeyPress(pauseController, KeyCode.S);
        assertEquals(2, getIndex(pauseController));
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
    /**
     * Utility to get the private field using a reflection.
     *
     * @param controller to be verified
     *
     * @return Integer value of the index
     *
     *
     **/

    private int getIndex(final Object controller) throws Exception {
        final Field field = controller.getClass().getDeclaredField("currentIndex");
        field.setAccessible(true);
        return (int) field.get(controller);
    }
}
