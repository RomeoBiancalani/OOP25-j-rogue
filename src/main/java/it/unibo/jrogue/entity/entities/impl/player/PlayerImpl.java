package it.unibo.jrogue.entity.entities.impl.player;

import java.util.Objects;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jrogue.commons.Dice;
import it.unibo.jrogue.commons.Move;
import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.entity.entities.api.Player;
import it.unibo.jrogue.entity.entities.impl.AbstractEntity;
import it.unibo.jrogue.entity.items.api.Equipment;
import it.unibo.jrogue.entity.items.api.Inventory;
import it.unibo.jrogue.entity.items.impl.Armor;
import it.unibo.jrogue.entity.items.impl.MeleeWeapon;
import it.unibo.jrogue.entity.items.impl.Ring;
import it.unibo.jrogue.entity.items.impl.SimpleInventory;

/**
 * Implementation of the player entity.
 * Manages inventory, equipment, experience and level progression.
 */
public class PlayerImpl extends AbstractEntity implements Player {

    private static final int INVENTORY_SIZE = 50;
    private static final int BASE_DAMAGE = 3;
    private static final int XP_TO_LEVEL_UP = 20;

    private int xp;
    private int gold;
    private final Inventory inventory;
    private Optional<Armor> armor;
    private Optional<MeleeWeapon> weapon;
    private Optional<Ring> ring;
    private boolean victory;

    /**
     * Construct a player with the specified attributes.
     * 
     * @param lifePoint     The initial life points.
     * @param level         The initial level.
     * @param armorClass    The base armor class.
     * @param startPosition The starting position on the map
     * 
     * @throws IllegalArgumentException if lifePoint or level isn't positive.
     * @throws IllegalArgumentException if startPosition is null.
     */
    public PlayerImpl(final int lifePoint, final int level, final int armorClass, final Position startPosition) {
        super(lifePoint, level, armorClass, startPosition);
        inventory = new SimpleInventory(INVENTORY_SIZE);
        armor = Optional.empty();
        weapon = Optional.empty();
        ring = Optional.empty();
        gold = 0;
        xp = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * If the player has a equipped ring its effect will activate.
     * </p>
     */
    @Override
    public void doMove(final Move move) {
        super.doMove(move);
        useRing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Inventory needs to be shared for GUI and interaction purposes")
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGold() {
        return gold;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException if amount is not positive.
     */
    @Override
    public void collectGold(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non positive");
        }
        gold = gold + amount;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Calculates the total armor class including the bonus from equipped armor
     * </p>
     */
    @Override
    public int getArmorClass() {
        int armorClassBonus = 0;
        if (armor.isPresent()) {
            armorClassBonus = armor.get().getBonus();
        }
        return super.getArmorClass() + armorClassBonus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttack() {
        int maxDamage = BASE_DAMAGE;
        int nDice = 1;
        if (weapon.isPresent()) {
            nDice++;
            maxDamage += weapon.get().getBonus();
        }
        return Dice.roll(nDice, maxDamage) + getLevel();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if equipment is null.
     */
    @Override
    public void equip(final Equipment equipment) {
        Objects.requireNonNull(equipment);
        if (equipment instanceof Armor newArmor) {
            this.equipArmor(newArmor);
        } else if (equipment instanceof MeleeWeapon newWeapon) {
            this.equipWeapon(newWeapon);
        } else if (equipment instanceof Ring newRing) {
            this.equipRing(newRing);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException     if equipment is null.
     * @throws IllegalArgumentException if equipment to remove is not the current
     *                                  equipped equipment.
     */
    @Override
    public void remove(final Equipment equipment) {
        Objects.requireNonNull(equipment, "Cannot unequip null item");
        if (!isEquipped(equipment)) {
            throw new IllegalArgumentException("You couldn't unequip an item that is not the equipped");
        }
        if (equipment.getClass().equals(Armor.class)) {
            this.removeArmor();
        } else if (equipment.getClass().equals(MeleeWeapon.class)) {
            this.removeWeapon();
        } else if (equipment.getClass().equals(Ring.class)) {
            this.removeRing();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if equipment is null.
     */
    public boolean isEquipped(final Equipment equipment) {
        Objects.requireNonNull(equipment, "Equipment to check must be not null");
        return equipment.equals(armor.orElse(null))
                || equipment.equals(weapon.orElse(null))
                || equipment.equals(ring.orElse(null));
    }

    /**
     * Equip the specified armor for the player.
     * 
     * @param armorToEquip The armor to be equipped.
     * @throws IllegalArgumentException if armor is null.
     */
    private void equipArmor(final Armor armorToEquip) {
        Objects.requireNonNull(armorToEquip, "Armor to equip must be not null");
        this.armor = Optional.of(armorToEquip);
    }

    /**
     * Remove the current equipped armor.
     * 
     * @throws IllegalStateException if player don't have a current equipped armor.
     */
    private void removeArmor() {
        if (this.armor.isEmpty()) {
            throw new IllegalStateException("You couldn't unequip armor if you don't have an equipped armor");
        }
        this.armor = Optional.empty();
    }

    /**
     * Equip the specified weapon for the player.
     * 
     * @param weaponToEquip the weapon to be equipped.
     * @throws IllegalArgumentException if weapon is null.
     */
    private void equipWeapon(final MeleeWeapon weaponToEquip) {
        Objects.requireNonNull(weaponToEquip);
        this.weapon = Optional.of(weaponToEquip);
    }

    /**
     * Remove the current equipped weapon.
     * 
     * @throws IllegalStateException if player don't have a current equipped weapon.
     */
    private void removeWeapon() {
        if (this.weapon.isEmpty()) {
            throw new IllegalStateException("You couldn't unequip weapon if you don't have an equipped weapon");
        }
        this.weapon = Optional.empty();
    }

    /**
     * Equip the specified ring for the player.
     * 
     * @param ringToEquip the ring to be equipped.
     * @throws IllegalArgumentException if weapon is null.
     */
    private void equipRing(final Ring ringToEquip) {
        Objects.requireNonNull(ringToEquip);
        this.ring = Optional.of(ringToEquip);
    }

    /**
     * Remove the current equipped ring.
     * 
     * @throws IllegalStateException if player don't have a current equipped ring.
     */
    private void removeRing() {
        if (this.ring.isEmpty()) {
            throw new IllegalStateException("You couldn't unequip ring if you don't have an equipped ring");
        }
        this.ring = Optional.empty();
    }

    /**
     * Use the equipped ring.
     */
    private void useRing() {
        if (ring.isPresent()) {
            this.heal(ring.get().getBonus());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException     if amount is null.
     * @throws IllegalArgumentException if amount is negative.
     */
    @Override
    public void collectXP(final int amount) {
        Objects.requireNonNull(amount, "Experience amount must be not null");
        if (amount < 0) {
            throw new IllegalArgumentException("Experience amount cannot be negative");
        }
        this.xp = xp + amount;
        while (xp >= XP_TO_LEVEL_UP) {
            levelUp();
            this.xp -= XP_TO_LEVEL_UP;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasArmor() {
        return !armor.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVictory(final boolean victory) {
        this.victory = victory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasWon() {
        return this.victory;
    }

}
