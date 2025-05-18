package dev.strwbry.eventhorizon.events.utility;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.listeners.PlayerInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MarkingUtility {

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

    public static void deleteAllMarkedEntities(NamespacedKey key) {
        EventHorizon.entityKeysToDelete.add(key);
        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().forEach(entity -> {
                if (isEntityMarked(entity, key)) {
                    entity.remove();
                }
            });
        });
    }

    // Player marking
    public static Player markPlayer(Player player, NamespacedKey key) {
        if (player == null) return null;
        player.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        return player;
    }

    public static boolean isPlayerMarked(Player player, NamespacedKey key) {
        if (player == null) return false;
        return player.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static Player unmarkPlayer(Player player, NamespacedKey key) {
        if (player == null) return null;
        if (player.getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
            player.getPersistentDataContainer().remove(key);
        }
        return player;
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

    public static void deleteAllMarkedItemStacks(NamespacedKey key) {
        EventHorizon.entityKeysToDelete.add(key);
        EventHorizon.getPlugin().getServer().getOnlinePlayers().forEach(PlayerInventoryListener::removeMarkedItems);
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

    public static void deleteAllMarkedItems(NamespacedKey key) {
        EventHorizon.entityKeysToDelete.add(key);
        Bukkit.getWorlds().forEach(world -> {
            world.getEntitiesByClass(Item.class).forEach(item -> {
                if (isItemMarked(item, key)) {
                    item.remove();
                }
            });
        });
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
