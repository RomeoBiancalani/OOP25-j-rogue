package it.unibo.rogue.entity.items.api;

import it.unibo.rogue.entity.entities.api.Player;

public interface Consumable extends Item{
    void consume(Player player);
}