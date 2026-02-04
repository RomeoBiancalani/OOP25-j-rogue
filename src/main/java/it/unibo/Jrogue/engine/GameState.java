package it.unibo.Jrogue.engine;

//Entity for Launch Menu
public class GameState {

    public enum State {
        MAIN_MENU,
        PLAYING,
        GAME_OVER
    }

    private State currentState;

    public GameState() {
        this.currentState = State.MAIN_MENU;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(final State newState) {
        this.currentState = newState;
        /*Keeping this debug line because it's cool*/
        System.out.println("Game state changed in: " + this.currentState);
    }



}
