package it.unibo.jrogue.boundary.api;

import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.world.api.GameMap;

/**
 * Defines the contract for game rendering and user interaction feedback. 
 */
public interface GameViewRenderer {

    /**
     * Displays a text message to the user.
     * Used for combat logs and item pickup notification.
     * 
     * @param message The mesage to show.
     */
    void displayMessage(String message);

    /**
     * Notify the GUI to refresh the player's vital statistic.
     * 
     * @param player The player to update stats from.
     * @param dungeonLevel the current dungeon level.
     */
    void updateStatus(Player player, int dungeonLevel);

    /**
     * Performs a full render of the game world.
     * 
     * @param map The current gameMap to be rendered.
     * @param player The player istanche.
     * @param dungeonLevel the current dungeon level
     */
    void renderAll(GameMap map, Player player, int dungeonLevel);

    /**
     * Initializes the view's graphical resources.
     * 
     * @param map The map used to configure the initial view state.
     */
    void initForMap(GameMap map);

}
