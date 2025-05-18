package dev.strwbry.eventhorizon.listeners;

import dev.strwbry.eventhorizon.EventHorizon;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ListenerManager
{
    private static EffectListener effectListener;
    private static EntityAddToWorldListener entityAddToWorldListener;
    private static PlayerDropItemListener playerDropItemListener;
    private static PlayerInventoryListener playerInventoryListener;

    // methods for initializing listeners
    public static void initializeEffectListener(){
        if (effectListener == null) {
            effectListener = new EffectListener();
            Bukkit.getPluginManager().registerEvents(effectListener, EventHorizon.getPlugin());
        }
    }
    public static void initializeEnityAddToWorld(){
        if (entityAddToWorldListener == null) {
            entityAddToWorldListener = new EntityAddToWorldListener();
            Bukkit.getPluginManager().registerEvents(entityAddToWorldListener, EventHorizon.getPlugin());
        }
    }
    public static void initializePlayerDropItem(){
        if (playerDropItemListener == null) {
            playerDropItemListener = new PlayerDropItemListener();
            Bukkit.getPluginManager().registerEvents(playerDropItemListener, EventHorizon.getPlugin());
        }
    }
    public static void initializePlayerInventory(){
        if (playerInventoryListener == null) {
            playerInventoryListener = new PlayerInventoryListener();
            Bukkit.getPluginManager().registerEvents(playerInventoryListener, EventHorizon.getPlugin());
        }
    }

    // methods to unregister listeners
    public static void unregisterAllListeners(){
        unregisterEffectListener();
        unregisterEntityAddToWorld();
        unregisterPlayerDropItem();
        unregisterPlayerInventory();
    }
    public static void unregisterEffectListener(){
        if (effectListener != null) {
            HandlerList.unregisterAll();
            effectListener = null;
        }
    }
    public static void unregisterEntityAddToWorld(){
        if (entityAddToWorldListener != null) {
            HandlerList.unregisterAll();
            entityAddToWorldListener = null;
        }
    }
    public static void unregisterPlayerDropItem(){
        if (playerDropItemListener != null) {
            HandlerList.unregisterAll();
            playerDropItemListener = null;
        }
    }
    public static void unregisterPlayerInventory(){
        if (playerInventoryListener != null) {
            HandlerList.unregisterAll();
            playerInventoryListener = null;
        }
    }


    // methods for getting listeners
    public static EffectListener getEffectListener(){
        return effectListener;
    }
    public static EntityAddToWorldListener getEntityAddToWorldListener(){
        return entityAddToWorldListener;
    }
    public static PlayerDropItemListener getPlayerDropItemListener(){
        return playerDropItemListener;
    }
    public static PlayerInventoryListener getPlayerInventoryListener(){
        return playerInventoryListener;
    }
}
