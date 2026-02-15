package it.unibo.jrogue.boundary.api;

/**
 * Inteface for the sounds.
 */
public interface SoundSystem {

    /**
     * Enum for all the kind of sounds.
     */
    enum Sound {
        EQUIP,
        DRINK,
        ATTACK,
        GOLD
    }

    /**
     * Method that plays the sound effect.
     * 
     * @param sound the type of sound to play.
     */
    void play(Sound sound);

    /**
     * Private method to load the sound file.
     * 
     * @param type the type of the sound effect.
     * 
     * @param path the path for the sound effect.
     */
    void load(Sound type, String path);
}
