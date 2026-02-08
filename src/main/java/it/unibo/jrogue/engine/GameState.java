package it.unibo.jrogue.engine;

/**Handles the State of the game*/

public final class GameState {
    /**
     * Possible states of the game*/
    public enum State {
        MAIN_MENU,
        PLAYING,
        GAME_OVER
    }

    private State currentState;

    /**
     * Initialization State*/

    public GameState() {
        this.currentState = State.MAIN_MENU;
    }

    public State getCurrentState() {
        return currentState;
    }
    /**
     * Setting the game State*/

    public void setCurrentState(final State newState) {
        this.currentState = newState;
    }
}
