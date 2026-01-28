package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.items.api.Consumable;

public class HealthPotion implements Consumable{
    private final int HEALING_AMOUNT = 10;
    @Override
    public String getDescription() {
        return "Pozione rossa (Recupera " + HEALING_AMOUNT + "HP";
    }

    @Override
    public void consume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consume'");
    }
    
}
