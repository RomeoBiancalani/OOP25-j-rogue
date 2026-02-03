package it.unibo.Jrogue.items.api;

import it.unibo.Jrogue.entities.api.Player;

public interface Equipment extends Item {
    void equip(Player player);
}
