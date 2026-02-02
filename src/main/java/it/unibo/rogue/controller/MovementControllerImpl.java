package it.unibo.rogue.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.rogue.controller.api.MovementController;
import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Enemy;
import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.entities.api.Entity;
import it.unibo.rogue.entity.items.api.Item;

/**
 * Controller responsible for managing movement for all entities.
 */
public class MovementControllerImpl implements MovementController {

    private final Player player;
    private final List<Enemy> enemies;
    private final Map<Position, Item> items;

    /**
     * Constructs a MovementController with the required game entities.
     * 
     * @param player The player entity.
     * @param enemies The list of current enemies in the level.
     * @param items The list of position of the items in the level
     * @throws NullPointerException if player or enemies is null.
     */
    public MovementControllerImpl(final Player player, final List<Enemy> enemies, final Map<Position, Item> items) {
        this.player = Objects.requireNonNull(player, "player cannot be null");
        this.enemies = Objects.requireNonNull(enemies, "enemies cannot be null");
        this.items = Objects.requireNonNull(items, "items cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeTurn(final Move move) {
        if (isValidMove(player, move)) {
            player.doMove(move);
            if (isOccupiedbyItem(player.getPosition())) {
                player.pickUpItem(items.get(player.getPosition()));
                items.remove(player.getPosition());
            }
        }

        enemies.stream()
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
        return !isOccupiedbyEntity(position);
        //TODO: aggiungere controllo per controllare che non ci si muova in un muro.
    }

    /**
     * Checks if position is currently occupied by an entity.
     * 
     * @param position The position to check.
     * @return true if an enemy is present at the position, false otherwise.
     */
    private boolean isOccupiedbyEntity(final Position position) {
        return position.equals(player.getPosition())
            || enemies.stream()
                      .filter(Enemy::isAlive)
                      .anyMatch(e -> e.getPosition().equals(position));
    }

    /**
     * Checks if position is currently occupied by an entity.
     * 
     * @param position The position to check
     * @return true if an item is present at the position, false otherwise.
     */
    private boolean isOccupiedbyItem(final Position position) {
        return items.containsKey(position);
    }
}
