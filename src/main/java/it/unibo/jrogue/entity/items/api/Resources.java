package it.unibo.jrogue.entity.items.api;

/**
 * Represents the resources in the game.
 */
public interface Resources extends Item {

    /**
     * Provides with the amount of the resource.
     * 
     * @return the amount of the resource.
     */
    int getAmount();
}
