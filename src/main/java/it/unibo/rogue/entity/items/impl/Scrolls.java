package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.items.api.Consumable;

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
    public void consume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consume'");
    }
    
}
