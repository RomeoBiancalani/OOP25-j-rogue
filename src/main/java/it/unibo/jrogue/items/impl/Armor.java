package it.unibo.jrogue.items.impl;

import it.unibo.jrogue.entities.api.Player;
import it.unibo.jrogue.items.api.Equipment;

public class Armor implements Equipment{
    private final String name;
    private final int protection;

    public Armor(String name,int protection) {
        this.name = name;
        this.protection = protection;
    }
    @Override
    public String getDescription() {
        return name + " (Defence: " + protection + ")";    
    }

    public int getProtection() {
        return protection;
    }
    @Override
    public void equip(Player player) {
        if (player == null) {
            return;
        } else {
            player.equipArmor(this);
            System.out.println("il player indossa: " + name);
        }

        
    }
    
}
