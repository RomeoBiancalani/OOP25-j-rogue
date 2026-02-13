package it.unibo.jrogue.engine;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializable container for game save data.
 * Stores all information needed to restore a game session.
 */
public final class SaveData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final long baseSeed;
    private final int currentLevel;
    private final PlayerData playerData;
    private final List<EnemyData> enemies;
    private final List<ItemData> items;

    /**
     * Creates a new SaveData.
     *
     * @param seed the base seed for level generation
     * @param currentLevel the current dungeon level
     * @param playerData the player's saved state
     * @param enemies the list of enemy states
     * @param items the list of item states on the map
     */
    public SaveData(final long seed, final int currentLevel,
                    final PlayerData playerData,
                    final List<EnemyData> enemies,
                    final List<ItemData> items) {
        this.baseSeed = seed;
        this.currentLevel = currentLevel;
        this.playerData = playerData;
        this.enemies = new ArrayList<>(enemies);
        this.items = new ArrayList<>(items);
    }

    /**
     * Returns the base seed.
     *
     * @return the base seed
     */
    public long getBaseSeed() {
        return baseSeed;
    }

    /**
     * Returns the current level.
     *
     * @return the current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Returns the player save data.
     *
     * @return the player data
     */
    public PlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Returns the enemy save data list.
     *
     * @return list of enemy data
     */
    public List<EnemyData> getEnemies() {
        return new ArrayList<>(enemies);
    }

    /**
     * Returns the item save data list.
     *
     * @return list of item data
     */
    public List<ItemData> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Saved state of a player.
     */
    public static final class PlayerData implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final int posX;
        private final int posY;
        private final int hp;
        private final int maxHp;
        private final int level;
        private final int armorClass;
        private final List<ItemData> inventoryItems;

        /**
         * Creates player save data.
         *
         * @param posX player x position
         * @param posY player y position
         * @param hp current hp
         * @param maxHp maximum hp
         * @param level player level
         * @param armorClass base armor class
         * @param inventoryItems items in inventory
         */
        public PlayerData(final int posX, final int posY, final int hp,
                          final int maxHp, final int level, final int armorClass,
                          final List<ItemData> inventoryItems) {
            this.posX = posX;
            this.posY = posY;
            this.hp = hp;
            this.maxHp = maxHp;
            this.level = level;
            this.armorClass = armorClass;
            this.inventoryItems = new ArrayList<>(inventoryItems);
        }

        /**
         * Returns the x position.
         *
         * @return x position
         */
        public int getPosX() {
            return posX;
        }

        /**
         * Returns the y position.
         *
         * @return y position
         */
        public int getPosY() {
            return posY;
        }

        /**
         * Returns the current hp.
         *
         * @return current hp
         */
        public int getHp() {
            return hp;
        }

        /**
         * Returns the max hp.
         *
         * @return max hp
         */
        public int getMaxHp() {
            return maxHp;
        }

        /**
         * Returns the player level.
         *
         * @return player level
         */
        public int getLevel() {
            return level;
        }

        /**
         * Returns the base armor class.
         *
         * @return base armor class
         */
        public int getArmorClass() {
            return armorClass;
        }

        /**
         * Returns the inventory items.
         *
         * @return inventory items
         */
        public List<ItemData> getInventoryItems() {
            return new ArrayList<>(inventoryItems);
        }
    }

    /**
     * Saved state of an enemy.
     */
    public static final class EnemyData implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String type;
        private final int posX;
        private final int posY;

        /**
         * Creates enemy save data.
         *
         * @param type the enemy type identifier
         * @param posX x position
         * @param posY y position
         */
        public EnemyData(final String type, final int posX, final int posY) {
            this.type = type;
            this.posX = posX;
            this.posY = posY;
        }

        /**
         * Returns the enemy type.
         *
         * @return the enemy type
         */
        public String getType() {
            return type;
        }

        /**
         * Returns the x position.
         *
         * @return x position
         */
        public int getPosX() {
            return posX;
        }

        /**
         * Returns the y position.
         *
         * @return y position
         */
        public int getPosY() {
            return posY;
        }
    }

    /**
     * Saved state of an item.
     */
    public static final class ItemData implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String type;
        private final int posX;
        private final int posY;
        private final String name;
        private final int value;

        /**
         * Creates item save data.
         *
         * @param type the item type identifier
         * @param posX x position on map (-1 for inventory)
         * @param posY y position on map (-1 for inventory)
         * @param name the item name (for named items)
         * @param value the item's numeric value (if needed, this can be use to cound value of items in inventory, value of gold etc)
         */
        public ItemData(final String type, final int posX, final int posY,
                        final String name, final int value) {
            this.type = type;
            this.posX = posX;
            this.posY = posY;
            this.name = name;
            this.value = value;
        }

        /**
         * Returns the item type.
         *
         * @return the item type
         */
        public String getType() {
            return type;
        }

        /**
         * Returns the x position.
         *
         * @return x position
         */
        public int getPosX() {
            return posX;
        }

        /**
         * Returns the y position.
         *
         * @return y position
         */
        public int getPosY() {
            return posY;
        }

        /**
         * Returns the item name.
         *
         * @return item name
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the item numeric value.
         *
         * @return item numeric value
         */
        public int getValue() {
            return value;
        }
    }
}
