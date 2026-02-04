package it.unibo.jrogue.items.api;

import it.unibo.jrogue.entities.api.Player;

public interface Consumable extends Item{
    void consume(Player player);
}