package it.unibo.jrogue.items.impl;

import java.util.Optional;
import java.util.Random;
import it.unibo.jrogue.items.api.Item;
import it.unibo.jrogue.items.api.ItemFactory;

public class ItemFactoryImpl implements ItemFactory{
    private final Random random = new Random();
    private final int BASE_DAMAGE_SWORD = 6;
    private final int BASE_DAMAGE_DAGGER = 4;
    private final int BASE_DAMAGE_AXE = 8;
    private final int BASE_DEF_HEAVY = 3;
    private final int BASE_DEF_LIGHT = 1;

    @Override
    public Item createStartingWeapon() {
        return new MeleeWeapon("Spada iniziale", BASE_DAMAGE_SWORD);
    }

    private int calculateDamage(int baseDamage,int level) {
        int scalingFactor = 2;
        int growth = scalingFactor * level;

        int variance = random.nextInt(3) -1 ;

        return baseDamage + growth + variance;
    }

    private int calculateProtection(int baseProtection, int level , boolean isHeavy) {
        int growth;
        if(isHeavy) {
            growth = level;
        } else growth = level / 2;
        
        int variance = random.nextInt(2);
        return baseProtection + growth + variance;
    }

    @Override
    public Item createRandomArmor(int level) {
        int armorDice = random.nextInt(100);

        if(armorDice < 70) {
            int def = calculateProtection(BASE_DEF_LIGHT,level,false);
            return new Armor("Armatura di cuoio", def);
        } else {
            int def = calculateProtection(BASE_DEF_HEAVY,level,true);
         return new Armor("Armatura di ferro", def);   
        }  
    }

    @Override
    public Optional<Item> createRandomItem(int level) {
        int roll = random.nextInt(100);

        if(roll < 20) {
        return Optional.empty();    
        }
        else if(roll <30) {
            return Optional.of(new HealthPotion());
        }
        // possibility to get a dagger(5%)
        else if(roll < 35) {
            int dmg = calculateDamage(BASE_DAMAGE_DAGGER,level);
            return Optional.of(new MeleeWeapon("pugnale",dmg));
        }
        // possibility to get a sword(5%)
        else if(roll < 40) {
            int dmg = calculateDamage(BASE_DAMAGE_SWORD,level);
            return Optional.of(new MeleeWeapon("spada",dmg));
        }
        // possibility to get an axe(5%)
        else if(roll < 45) {
            int dmg = calculateDamage(BASE_DAMAGE_AXE,level);
            return Optional.of(new MeleeWeapon("ascia",dmg));
        } 
        //possibility to get an armour(5%)
        else if(roll < 50) {
            return Optional.of(createRandomArmor(level));
        } else return Optional.empty();
    }   
}
