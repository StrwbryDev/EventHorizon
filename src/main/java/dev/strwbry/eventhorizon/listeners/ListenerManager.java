package dev.strwbry.eventhorizon.listeners;

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
        }
    }
    public static void initializeEnityAddToWorld(){
        if (entityAddToWorldListener == null) {
            entityAddToWorldListener = new EntityAddToWorldListener();
        }
    }
    public static void initializePlayerDropItem(){
        if (playerDropItemListener == null) {
            playerDropItemListener = new PlayerDropItemListener();
        }
    }
    public static void initializePlayerInventory(){
        if (playerInventoryListener == null) {
            playerInventoryListener = new PlayerInventoryListener();
        }
    }

    // methods to unregister listeners
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
