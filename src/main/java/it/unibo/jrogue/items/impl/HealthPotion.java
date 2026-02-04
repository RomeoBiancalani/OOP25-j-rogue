package it.unibo.jrogue.items.impl;

import it.unibo.jrogue.entities.api.Player;
import it.unibo.jrogue.items.api.Consumable;

public class HealthPotion implements Consumable{
    private final int HEALING_AMOUNT = 10;
    @Override
    public String getDescription() {
        return "Pozione rossa (Recupera " + HEALING_AMOUNT + "HP)";
    }

    @Override
    public void consume(Player player) {
        if(player == null){
          return;  
        } else {
            //player.heal(HEALING_AMOUNT);
            //System.out.println("Hai bevuto la pozione");
        }

        
    }
    
}
