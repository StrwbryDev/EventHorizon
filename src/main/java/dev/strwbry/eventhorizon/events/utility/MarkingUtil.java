package dev.strwbry.eventhorizon.events.utility;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class MarkingUtil {

    // Entity marking
    public static Entity markEntity(Entity entity, NamespacedKey key) {
        if (entity == null) return null;
        entity.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        return entity;
    }

    public static boolean isEntityMarked(Entity entity, NamespacedKey key) {
        if (entity == null) return false;
        return entity.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static Entity unmarkEntity(Entity entity, NamespacedKey key) {
        if (entity == null) return null;
        if (entity.getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
            entity.getPersistentDataContainer().remove(key);
        }
        return entity;
    }

    // ItemStack marking
    public static ItemStack markItemStack(ItemStack item, NamespacedKey key) {
        if (item == null || item.getType().isAir()) return null;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isItemStackMarked(ItemStack item, NamespacedKey key) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static ItemStack unmarkItemStack(ItemStack item, NamespacedKey key) {
        if (item == null || !item.hasItemMeta()) return item;

        ItemMeta meta = item.getItemMeta();
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
            meta.getPersistentDataContainer().remove(key);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Item marking
    public static Item markItem(Item item, NamespacedKey key) {
        if (item == null) return null;
        item.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        return item;
    }

    public static boolean isItemMarked(Item item, NamespacedKey key) {
        if (item == null) return false;
        return item.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static Item unmarkItem(Item item, NamespacedKey key) {
        if (item == null) return null;

        if (item.getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
            item.getPersistentDataContainer().remove(key);
        }
        return item;
    }

    // TileState marking
    public static boolean markTileState(TileState tileState, NamespacedKey key) {
        if (tileState == null) return false;

        tileState.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        return tileState.update();
    }

    public static boolean isTileStateMarked(TileState tileState, NamespacedKey key) {
        if (tileState == null) return false;
        return tileState.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static boolean unmarkTileState(TileState tileState, NamespacedKey key) {
        if (tileState == null) return false;

        if (tileState.getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
            tileState.getPersistentDataContainer().remove(key);
        }
        return tileState.update();
    }

}
