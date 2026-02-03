package it.unibo.Jrogue.engine;

//Entity for Launch Menu
public class GameState {

    public enum State {
        MAIN_MENU,
        PLAYING,
        GAME_OVER
    }

    private State CurrentState;

    public GameState() {
        this.CurrentState = State.MAIN_MENU;
    }

    public State getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(State newState) {
        this.CurrentState = newState;
        /*Keeping this debug line because it's cool*/
        System.out.println("Game state changed in: " + this.CurrentState);
    }



}
