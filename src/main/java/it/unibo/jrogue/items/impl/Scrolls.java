package it.unibo.Jrogue.items.impl;

import it.unibo.Jrogue.entities.api.Player;
import it.unibo.Jrogue.items.api.Consumable;

public class Scrolls implements Consumable{
    private final String name;
    Scrolls(String name) {
        this.name = name;
    }
    @Override
    public String getDescription() {
        return name + " ";
    }
    @Override
    public void consume(Player target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consume'");
    }
    
}
