package it.unibo.jrogue.items.api;

import it.unibo.jrogue.entities.api.Player;

public interface Equipment extends Item {
    void equip(Player player);
}
