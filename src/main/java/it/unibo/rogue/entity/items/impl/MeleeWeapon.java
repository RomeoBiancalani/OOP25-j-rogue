package it.unibo.rogue.entity.items.impl;

import it.unibo.rogue.entity.items.api.Equipment;

public class MeleeWeapon implements Equipment{
    private final String name;
    private final int damage;
    public MeleeWeapon(String name,int damage) {
        this.name = name;
        this.damage = damage;
    }
    @Override
    public String getDescription() {
        return name + " (Danno " + damage + ")";
    }

    @Override
    public void equip() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equip'");
    }
    
}
