package it.unibo.rogue.entity.entities.impl.player;

import java.util.Objects;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.rogue.entity.Dice;
import it.unibo.rogue.entity.Move;
import it.unibo.rogue.entity.Position;
import it.unibo.rogue.entity.entities.api.Player;
import it.unibo.rogue.entity.entities.impl.AbstractEntity;
import it.unibo.rogue.entity.items.api.Equipment;
import it.unibo.rogue.entity.items.api.Inventory;
import it.unibo.rogue.entity.items.impl.Armor;
import it.unibo.rogue.entity.items.impl.MeleeWeapon;
import it.unibo.rogue.entity.items.impl.SimpleInventory;

/**
 * Implementation of the player entity.
 * Manages inventory, equipment, experience and level progression.
 */
public class PlayerImpl extends AbstractEntity implements Player {

    private static final int INVENTORY_SIZE = 50;
    private static final int BASE_DAMAGE = 3;
    private static final int XP_TO_LEVEL_UP = 20;

    private int xp;
    private final Inventory inventory;
    private Optional<Armor> armor;
    private Optional<MeleeWeapon> weapon;
    private Optional<Equipment> ring;

    /**
     * Construct a player with the specified attributes.
     * 
     * @param lifePoint     The initial life points.
     * @param level         The initial level.
     * @param armorClass    The base armor class.
     * @param startPosition The starting position on the map
     * @throws IllegalArgumentException if lifePoint or level isn't positive.
     * @throws IllegalArgumentException if startPosition is null.
     */
    public PlayerImpl(final int lifePoint, final int level, final int armorClass, final Position startPosition) {
        super(lifePoint, level, armorClass, startPosition);
        inventory = new SimpleInventory(INVENTORY_SIZE);
        armor = Optional.empty();
        weapon = Optional.empty();
        xp = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * If the player has a equipped ring his effect will activate.
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
     * 
     * <p>
     * Calculates the total armor class including the bonus from equipped armor
     * </p>
     */
    @Override
    public int getArmorClass() {
        int armorClassBonus = 0;
        if (armor.isPresent()) {
            armorClassBonus = armor.get().getProtection();
        }
        return super.getArmorClass() + armorClassBonus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHitBonus() {
        int maxDamage = BASE_DAMAGE;
        if (weapon.isPresent()) {
            maxDamage += weapon.get().getDamage();
        }
        return Dice.roll(getLevel(), maxDamage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void equip(final Equipment equipment) {
        Objects.requireNonNull(equipment, "Cannot equip null item").equip(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void equipArmor(final Armor armorToEquip) {
        this.armor = Optional.ofNullable(armorToEquip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void equipWeapon(final MeleeWeapon weaponToEquip) {
        this.weapon = Optional.ofNullable(weaponToEquip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void equipRing(final Equipment ringToEquip) {
        this.ring = Optional.ofNullable(ringToEquip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useRing() {
        if (ring.isPresent()) {
            //ring.get().activateEffect(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collectXP(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Experience amount cannot be null");
        }
        this.xp = xp + amount;
        while (xp >= XP_TO_LEVEL_UP) {
            levelUp();
            this.xp -= XP_TO_LEVEL_UP;
        }
    }

}
