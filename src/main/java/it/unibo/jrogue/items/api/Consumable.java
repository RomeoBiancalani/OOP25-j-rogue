package it.unibo.Jrogue.items.api;

import it.unibo.Jrogue.entities.api.Player;

public interface Consumable extends Item{
    void consume(Player player);
}