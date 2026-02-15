package it.unibo.jrogue.boundary;

import javafx.scene.media.AudioClip;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.jrogue.boundary.api.SoundSystem;

/**
 * Class that implements the SoundSystem interface.
 */
public class SoundManager implements SoundSystem {

    private final Map<Sound, AudioClip> sounds = new EnumMap<>(SoundSystem.Sound.class);

    /**
     * Constructor for the SoundManager.
     */
    public SoundManager() {
        load(Sound.EQUIP, "sounds/equip.wav");
        load(Sound.DRINK, "sounds/drink.wav");
        load(Sound.ATTACK, "sounds/hit.wav");
        load(Sound.GOLD, "sounds/gold.wav");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void load(final Sound type, final String path) {
        final String uri = Objects.requireNonNull(getClass().getClassLoader().getResource(path)).toExternalForm();
        sounds.put(type, new AudioClip(uri));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Sound type) {
        if (sounds.containsKey(type)) {
            sounds.get(type).play();
        }
    }
}
