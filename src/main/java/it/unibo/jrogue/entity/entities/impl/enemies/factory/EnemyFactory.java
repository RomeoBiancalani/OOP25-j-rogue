package it.unibo.jrogue.entity.entities.impl.enemies.factory;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.Enemy;

/**
 * Factory interface to create different type of enemies.
 */
public interface EnemyFactory {

    /**
     * Creates a bat enemy.
     * 
     * @param Position The spawn position.
     * @param level The curren dungeon level.
     * @return a new Bat.
     */
    Enemy createBat(Position Position, int level);

    /**
     * Creates a HobGoblin enemy.
     * 
     * @param position The spawn position.
     * @param level The curren dungeon level.
     * @return a new HobGoblin.
     */
    Enemy createHobGoblin(Position position, int level);

    /**
     * Creates a Dragon enemy.
     * 
     * @param position The spawn position.
     * @param level The curren dungeon level.
     * @return a new Dragon.
     */
    Enemy createDragon(Position position, int level);

    /**
     * Creates a random enemy based on the dungeon level.
     * 
     * @param position The spawn position.
     * @param level The curren dungeon level.
     * @return An Optional containing a new Enemy, or empty if no enemy is generated.
     */
    Enemy createRandomEnemy(Position pos, int level);
}
