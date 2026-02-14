package it.unibo.jrogue.entity.items.impl;

import java.util.Optional;

import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.items.api.Item;
import it.unibo.jrogue.entity.items.api.ItemFactory;

/**
 * Implementation of the ItemFactory.
 */
public class ItemFactoryImpl implements ItemFactory {

    private static final int BASE_GOLD_AMOUNT = 10;
    private static final int GOLD_RANDOM_BOUND = 20;

    private static final int BASE_DAMAGE_SWORD = 6;
    private static final int BASE_DAMAGE_DAGGER = 4;
    private static final int BASE_DAMAGE_SHOVEL = 8;

    private static final int BASE_DEF_HEAVY = 3;
    private static final int BASE_DEF_LIGHT = 1;

    private static final int BASE_RING_HEALING = 2;
    private static final int RING_HEALING_BOUND = 5;

    private static final int SCALING_FACTOR = 2;
    private static final int DAMAGE_VARIANCE_BOUND = 3;
    private static final int PROTECTION_VARIANCE_BOUND = 2;

    /*
     * private static final int SPIKE_TRAP_DAMAGE = 5;
     * private static final int POISON_TRAP_DAMAGE = 2;
     * private static final int TELEPORT_TRAP_DAMAGE = 0;
     */

    private static final int ROLL_MAX = 100;
    private static final int CHANCE_RESOURCE = 20;
    private static final int CHANCE_POTION = 30;
    private static final int CHANCE_DAGGER = 35;
    private static final int CHANCE_SWORD = 40;
    private static final int CHANCE_SHOVEL = 45;
    private static final int CHANCE_ARMOR = 50;
    private static final int CHANCE_RING = 55;
    private static final int CHANGE_FOOD = 60;
    private static final int CHANGE_SCROLL = 65;

    private static final int ARMOR_SELECTION = 70;

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createStartingWeapon() {
        return new MeleeWeapon("Spada iniziale", BASE_DAMAGE_SWORD);
    }

    /**
     * Calculates the damage of the item generated
     * taking in consideration: level the player
     * currently is, baseDamage of the weapon
     * and a random variance.
     *
     * @param baseDamage base damage of the weapon.
     *
     * @param level      the level the player currently is.
     *
     * @return the damage of the weapon generated.
     */
    private int calculateDamage(final int baseDamage, final int level) {
        final int growth = level / SCALING_FACTOR;

        final int variance = GameRandom.nextInt(DAMAGE_VARIANCE_BOUND) - 1;

        return baseDamage + growth + variance;
    }

    /**
     * Calculates the protection of the armor
     * taking in consideration: level the player
     * currently is, baseProtection of the armor
     * and a random variance.
     *
     * @param baseProtection the base protection of the armor.
     *
     * @param level          the level the player currently is.
     *
     * @param isHeavy        true if the armor is heavy.
     *
     * @return the protection of the armor generated.
     */
    private int calculateProtection(final int baseProtection, final int level, final boolean isHeavy) {
        int growth = level / SCALING_FACTOR;
        if (isHeavy) {
            growth += 1;
        }

        final int variance = GameRandom.nextInt(PROTECTION_VARIANCE_BOUND);
        return baseProtection + growth + variance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createRandomArmor(final int level) {
        final int armorDice = GameRandom.nextInt(ROLL_MAX);

        if (armorDice < ARMOR_SELECTION) {
            final int def = calculateProtection(BASE_DEF_LIGHT, level, false);
            return new Armor("Armatura di cuoio", def);
        } else {
            final int def = calculateProtection(BASE_DEF_HEAVY, level, true);
            return new Armor("Armatura di ferro", def);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Item> createRandomItem(final int level) {
        final int roll = GameRandom.nextInt(ROLL_MAX);
        if (level == 10) {
            return Optional.of(createAmulet());
        }
        if (roll < CHANCE_RESOURCE) {
            return Optional.of(createRandomGold());
        } else if (roll < CHANCE_POTION) {
            return Optional.of(createHealthPotion());
        } else if (roll < CHANCE_DAGGER) {
            return Optional.of(createWeapon("Pugnale", level));
        } else if (roll < CHANCE_SWORD) {
            return Optional.of(createWeapon("Spada", level));
        } else if (roll < CHANCE_SHOVEL) {
            return Optional.of(createWeapon("Pala", level));
        } else if (roll < CHANCE_ARMOR) {
            return Optional.of(createRandomArmor(level));
        } else if (roll < CHANCE_RING) {
            return Optional.of(createRandomArmor(level));
        } else if (roll < CHANGE_FOOD) {
            return Optional.of(createFood());
        } else if (roll < CHANGE_SCROLL) {
            return Optional.of(createScroll());
        } else {
            return Optional.empty();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createHealthPotion() {
        return new HealthPotion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createFood() {
        return new Food();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createRandomGold() {
        final int amount = BASE_GOLD_AMOUNT + GameRandom.nextInt(GOLD_RANDOM_BOUND) + 1;
        return new Gold(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createScroll() {
        return new Scroll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createRandomRing() {
        final int healing = BASE_RING_HEALING + GameRandom.nextInt(RING_HEALING_BOUND) + 1;
        return new Ring("Healing ring", healing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createWeapon(final String name,final int level) {
        int baseDmg = BASE_DAMAGE_SWORD;
        final String currentName = name;
        if (currentName.toLowerCase().contains("pugnale")) {
            baseDmg = BASE_DAMAGE_DAGGER;
        } else if (currentName.toLowerCase().contains("pala")) {
            baseDmg = BASE_DAMAGE_SHOVEL;
        }
        final int finalDmg = calculateDamage(baseDmg, level);

        return new MeleeWeapon(name, finalDmg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item createAmulet() {
        return new Amulet();
    }
}
