package it.unibo.jrogue.entity.world.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.world.api.Trap;
import it.unibo.jrogue.controller.DungeonController;

public class TeleportTrap implements Trap {

    private final Position position;
    private boolean active;
    private boolean discovered;

    public TeleportTrap(Position position){
        this.position = position;
        this.active = true;
        this.discovered = false;
    }
    //Nota: per ora facciamo che teleportBack chiama trigger e non viceversa a causa del controller
    public void teleportBack(final DungeonController controller) {
        if (!this.active) {
            return;
        }
        controller.previousLevel();
        this.trigger();
    }

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void trigger(){
        this.active = false;
    }

    @Override
    public Position getPosition(){
        return position;
    }

    @Override
    public void discover(){
        discovered = true;
    }

    @Override
    public boolean isDiscovered() {
        return discovered;
    }

}
