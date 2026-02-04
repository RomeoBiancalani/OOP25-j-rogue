package it.unibo.jrogue.items.impl;

import it.unibo.jrogue.entities.api.Player;
import it.unibo.jrogue.items.api.Equipment;

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
    
    public int getDamage() {
        return damage;
    }

    @Override
    public void equip(Player player) {
        if(player == null) {
        return;
        } else {
            player.equipWeapon(this);
            System.out.println("Il player impugna: " + name);
        }  
    }
    
}
