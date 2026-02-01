package it.unibo.rogue.entity.items.api;

import it.unibo.rogue.entity.entities.api.Player;

public interface Equipment extends Item {
    void equip(Player player);
}
