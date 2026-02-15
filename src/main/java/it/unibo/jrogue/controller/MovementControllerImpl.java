package it.unibo.jrogue.controller;

import java.util.Objects;
import java.util.Optional;

import it.unibo.jrogue.boundary.api.GameViewRenderer;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.api.MovementController;
import it.unibo.jrogue.controller.api.CombatController;
import it.unibo.jrogue.entity.entities.api.Enemy;
import it.unibo.jrogue.entity.entities.api.Entity;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.items.impl.Amulet;
import it.unibo.jrogue.entity.items.impl.Gold;
import it.unibo.jrogue.entity.world.api.GameMap;

/**
 * Controller responsible for managing movement for all entities.
 * Uses GameMap to access all game state.
 */
public class MovementControllerImpl implements MovementController {

    private final CombatController combatController = new CombatControllerImpl();

    private final GameViewRenderer renderer;
    private final GameMap gameMap;
    private final Player player;

    /**
     * Constructs a MovementController with a GameMap containing all game state.
     *
     * @param gameMap  The game map containing player, enemies, items, and terrain.
     * @param renderer The DungeonRenderer to render resources
     * @throws NullPointerException  if gameMap is null.
     * @throws NullPointerException  if renderer is null.
     * @throws IllegalStateException if gameMap has no player set.
     */
    public MovementControllerImpl(final GameMap gameMap, final GameViewRenderer renderer) {
        this.gameMap = Objects.requireNonNull(gameMap, "gameMap cannot be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer cannot be null");
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

            // Trigger trap if present at the moved position
            gameMap.getTrapAt(player.getPosition())
                .ifPresent(trap -> {
                    // TODO: insert trap trigger logic
                    // trap.trigger(player);
                    renderer.displayMessage("Sei caduto in una trappola: " + trap.getDescription());
                });

            // Pick up item if present at the moved position
            gameMap.removeItemAt(player.getPosition())
                    .ifPresent(item -> {
                        if (item instanceof Amulet) {
                            player.setVictory(true);
                        }
                        if (item instanceof Gold gold) {
                            player.collectGold(gold.getAmount());

                        } else {
                            player.getInventory().addItem(item);
                        }
                        renderer.displayMessage("Hai raccolto: " + item.getDescription());
                    });
        } else {
            final Optional<Enemy> target = getOccupiedByEnemy(player, move);
            if (target.isPresent()) {
                final int damage = combatController.attack(player, target.get());
                if (damage <= 0) {
                    renderer.displayMessage("Hai mancato il nemico");
                } else {
                    renderer.displayMessage("Hai colpito il nemico causandogli " + damage + " di danno");
                }
                // If player killed the enemy, collect his drop and xp.
                if (!target.get().isAlive()) {
                    String message = "Il nemico é morto";
                    final Optional<Item> drop = target.get().getItemDrop();
                    if (drop.isPresent()) {
                        if (drop.get() instanceof Gold gold) {
                            player.collectGold(gold.getAmount());
                        } else {
                            player.getInventory().addItem(drop.get());
                        }
                        message = message + " e ti ha lasciato: " + drop.get().getDescription();
                    }
                    player.collectXP(target.get().getXpDrop());
                    renderer.displayMessage(message);
                }
            }
        }

        // Move all awake enemies
        gameMap.getEnemies().stream()
                .filter(e -> e.isAlive() && !e.isSleeping())
                .forEach(e -> {
                    final Move eMove = e.getNextMove(player.getPosition());
                    if (isValidMove(e, eMove)) {
                        e.doMove(eMove);
                    } else {
                        final Position position = eMove.applyToPosition(e.getPosition());
                        if (isOccupiedByPlayer(position) && player.isAlive()) {
                            final int damage = combatController.attack(e, player);
                            if (damage <= 0) {
                                renderer.displayMessage("Il nemico ti ha mancato");
                            } else {
                                renderer.displayMessage("Il nemico ti ha colpito causandoti " + damage + " di danno");
                            }
                        }
                    }
                });
    }

    /**
     * Validates whether a move is legal for a specific entity.
     * 
     * @param entity The entity attempting to move.
     * @param move   The move that the entity is attempting.
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
     * @return true if an entity is present at the position, false otherwise.
     */
    private boolean isOccupiedByEntity(final Position position) {
        return isOccupiedByPlayer(position) || isOccupiedByEnemy(position);
    }

    /**
     * Checks if position is currently occupied by the Player.
     * 
     * @param position The position to check.
     * @return true if player is present at the position, false otherwise.
     */
    private boolean isOccupiedByPlayer(final Position position) {
        return position.equals(player.getPosition());
    }

    /**
     * Checks if position is currently occupied by an enemy.
     * 
     * @param position The position to check.
     * @return true if an enemy is present at the position, false otherwise.
     */
    private boolean isOccupiedByEnemy(final Position position) {
        return gameMap.getEnemies().stream()
                .filter(Enemy::isAlive)
                .anyMatch(e -> e.getPosition().equals(position));
    }

    /**
     * Finds the enemy that is occupying the position resulting from the entity's
     * move.
     * 
     * @param entity The entity performing the check.
     * @param move   The move to simulate.
     * @return An Optional containing the enemy if found, empty otherwise.
     */
    private Optional<Enemy> getOccupiedByEnemy(final Entity entity, final Move move) {
        final Position position = move.applyToPosition(entity.getPosition());
        return gameMap.getEnemies().stream()
                .filter(Enemy::isAlive)
                .filter(e -> e.getPosition().equals(position))
                .findAny();
    }
}
