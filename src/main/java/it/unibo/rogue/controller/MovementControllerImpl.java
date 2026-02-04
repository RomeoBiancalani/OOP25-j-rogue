package it.unibo.rogue.controller;

import java.util.Objects;

import it.unibo.rogue.commons.Move;
import it.unibo.rogue.commons.Position;
import it.unibo.rogue.controller.api.MovementController;
import it.unibo.rogue.entity.gameEntities.api.Enemy;
import it.unibo.rogue.entity.gameEntities.api.Entity;
import it.unibo.rogue.entity.gameEntities.api.Player;
import it.unibo.rogue.entity.world.api.GameMap;

/**
 * Controller responsible for managing movement for all entities.
 * Uses GameMap to access all game state.
 */
public class MovementControllerImpl implements MovementController {

    private final GameMap gameMap;
    private final Player player;

    /**
     * Constructs a MovementController with a GameMap containing all game state.
     *
     * @param gameMap The game map containing player, enemies, items, and terrain.
     * @throws NullPointerException if gameMap is null.
     * @throws IllegalStateException if gameMap has no player set.
     */
    public MovementControllerImpl(final GameMap gameMap) {
        this.gameMap = Objects.requireNonNull(gameMap, "gameMap cannot be null");
        if (gameMap.getPlayer().isEmpty()) {
            throw new IllegalStateException("GameMap must have a player set");
        }
        this.player = gameMap.getPlayer().get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeTurn(final Move move) {
        if (isValidMove(player, move)) {
            player.doMove(move);

            // Pick up item if present at the moved position
            gameMap.removeItemAt(player.getPosition())
                   .ifPresent(player::pickUpItem);
        }

        // Move all awake enemies
        gameMap.getEnemies().stream()
               .filter(e -> e.isAlive() && !e.isSleeping())
               .forEach(e -> {
                    final Move eMove = e.getNextMove(player.getPosition());
                    if (isValidMove(e, eMove)) {
                        e.doMove(eMove);
                    }
               });
    }

    /**
     * Validates whether a move is legal for a specific entity.
     * 
     * @param entity The entity attempting to move.
     * @param move The move that the entity is attempting.
     * @return true if the destination is not occupied and it is not a wall,
     *         false otherwise.
     */
    private boolean isValidMove(final Entity entity, final Move move) {
        if (move == Move.IDLE) {
            return true;
        }
        final Position position = move.applyToPosition(entity.getPosition());
        return !isOccupiedByEntity(position) && gameMap.isWalkable(position);
    }

    /**
     * Checks if position is currently occupied by an entity.
     * 
     * @param position The position to check.
     * @return true if an enemy is present at the position, false otherwise.
     */
    private boolean isOccupiedByEntity(final Position position) {
        return position.equals(player.getPosition())
            || gameMap.getEnemies().stream()
                .filter(Enemy::isAlive)
                .anyMatch(e -> e.getPosition().equals(position));
    }
}
