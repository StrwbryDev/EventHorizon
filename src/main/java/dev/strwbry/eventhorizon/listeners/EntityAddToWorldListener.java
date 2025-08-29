package dev.strwbry.eventhorizon.listeners;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.attributes.ZeroGravity;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

/**
 * Listener class responsible for managing entities when they are added to the world.
 * This class handles the removal of marked entities as they enter the world,
 * ensuring that entities marked for deletion are removed immediately.
 * Also applies zero gravity to new entities when the ZeroGravity event is active.
 *
 * Implements Bukkit's Listener interface to handle specific events
 * related to entity world entry management.
 */
public class EntityAddToWorldListener implements Listener {

    /**
     * Handles the EntityAddToWorldEvent by removing marked entities when they enter the world.
     * This method is called automatically when an entity is added to the world.
     * Also applies zero gravity to new entities when the ZeroGravity event is active.
     *
     * @param event The EntityAddToWorldEvent containing information about the entity being added
     */
    @EventHandler
    public void onEntityAddedToWorld(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        
        // Handle marked entities for deletion
        EventHorizon.entityKeysToDelete.forEach(key -> {
            if (isEntityMarked(entity, key)) {
                entity.remove();
            }
        });
        
        // Apply zero gravity to new entities if ZeroGravity event is active
        if (EventHorizon.getEventManager().getCurrentEvent() instanceof ZeroGravity) {
            if (!(entity instanceof Player)) {
                if (entity instanceof Item) {
                    // Items get zero gravity (stay floating)
                    entity.setGravity(false);
                } else if (entity instanceof LivingEntity) {
                    // Mobs get reduced gravity - disable normal gravity, custom gravity applied in task
                    entity.setGravity(false);
                } else {
                    // Other entities (projectiles, etc.) get zero gravity
                    entity.setGravity(false);
                }
            }
        }
    }

    /**
     * Checks if an entity is marked with a specific NamespacedKey.
     * Used to identify entities that should be removed from the world.
     *
     * @param entity The Entity to check for marking
     * @param key The NamespacedKey to look for in the entity's persistent data
     * @return true if the entity is marked with the specified key, false otherwise
     */
    public boolean isEntityMarked(Entity entity, NamespacedKey key) {
        return entity.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }
}
