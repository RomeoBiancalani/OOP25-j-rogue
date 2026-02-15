package it.unibo.jrogue.entity.world.api;

import it.unibo.jrogue.commons.Position;
import java.util.Optional;

/**
 * Interface for the factory creating traps.
 */

public interface TrapFactory {

    /**
     * Creates a random trap based on the dungeon level.
     *
     * @param position where the trap spawn
     *
     * @param level   dungeon level
     *
     * @return Optional with the trap in it or empty if not present
     */

    Optional<Trap> createRandomTrap(Position position, int level);

    /**
     * Creates a simple Rock Trap.
     *
     * @param position where the trap spawn
     *
     * @return RockTrap
     */

    Trap createRockTrap(Position position);

    /**
     * Creates a Pit of Spikes Traps.
     *
     * @param  position where the trap spawns
     * @return PitOfSpikes
     */

    Trap createPitOfSpikesTrap(Position position);

    /**
     * Creates a Teleport Trap.
     *
     * @param position the position of the trap
     * @return a new TeleportTrap
     */

    Trap createTeleportTrap(Position position);
}