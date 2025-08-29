package dev.strwbry.eventhorizon.events.attributes;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event that simulates zero gravity conditions in the game.
 * This event modifies player gravity and movement speed attributes, and affects all entities including dropped items.
 * Mobs have reduced gravity (like players), while items have zero gravity (stay floating).
 * Extends {@link BaseAttribute} and is classified as a NEUTRAL event.
 */
public class ZeroGravity extends BaseAttribute {

    /** List to track entities that had their gravity disabled */
    private List<Entity> affectedEntities = new ArrayList<>();
    /** Task that continuously applies zero gravity to entities */
    private BukkitTask gravityTask;
    /** Fall speed multiplier for mobs (0.0 = no fall, 1.0 = normal fall) */
    private static final double MOB_FALL_SPEED_MULTIPLIER = 0.05; // 5% of normal fall speed
    /** Upward velocity for items to make them float */
    private static final double ITEM_FLOAT_VELOCITY = 0.1;

    /**
     * Constructs a new ZeroGravity event.
     * Initializes the event with NEUTRAL classification and applies gravity and movement speed modifiers.
     */
    public ZeroGravity() {
        super(EventClassification.NEUTRAL, "zeroGravity");
        
        addAttributeModifier(Attribute.GRAVITY, -0.05, AttributeModifier.Operation.ADD_NUMBER);
        addAttributeModifier(Attribute.MOVEMENT_SPEED, 0.05, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
    }

    /**
     * Applies zero gravity effect to all entities in all worlds, including dropped items.
     * Players are excluded from this effect as they are handled through attributes.
     * Mobs get reduced gravity, items get zero gravity.
     */
    public void applyZeroGravityToEntities() {
        affectedEntities.clear();
        
        for (org.bukkit.World world : EventHorizon.getPlugin().getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Player)) {
                    // Store entities that had gravity enabled before we disable it
                    if (entity.hasGravity()) {
                        affectedEntities.add(entity);
                    }
                    
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
    }

    /**
     * Starts a repeating task that continuously applies zero gravity to entities.
     * This ensures the effect persists even when entities are affected by other forces.
     */
    public void startGravityTask() {
        gravityTask = EventHorizon.getPlugin().getServer().getScheduler().runTaskTimer(EventHorizon.getPlugin(), () -> {
            for (org.bukkit.World world : EventHorizon.getPlugin().getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (!(entity instanceof Player) && entity.isValid() && !entity.isDead()) {
                        if (entity instanceof Item) {
                            // Items: ensure zero gravity and make them float up
                            if (entity.hasGravity()) {
                                entity.setGravity(false);
                            }
                            // Give items a slight upward velocity to make them float
                            if (entity.getVelocity().getY() <= 0) {
                                entity.setVelocity(entity.getVelocity().setY(ITEM_FLOAT_VELOCITY));
                            }
                        } else if (entity instanceof LivingEntity) {
                            // Mobs: apply reduced gravity effect by manipulating velocity
                            // Disable normal gravity and apply custom reduced gravity
                            if (entity.hasGravity()) {
                                entity.setGravity(false);
                            }
                            // Apply reduced gravity effect using configurable multiplier
                            double currentY = entity.getVelocity().getY();
                            // Apply a small downward force (reduced gravity)
                            double newY = currentY - (0.08 * MOB_FALL_SPEED_MULTIPLIER); // 0.08 is normal gravity per tick
                            entity.setVelocity(entity.getVelocity().setY(newY));
                        } else {
                            // Other entities: ensure zero gravity
                            if (entity.hasGravity()) {
                                entity.setGravity(false);
                            }
                            if (entity.getVelocity().getY() < 0) {
                                entity.setVelocity(entity.getVelocity().setY(0));
                            }
                        }
                    }
                }
            }
        }, 1L, 1L); // Run every tick
    }

    /**
     * Stops the repeating gravity task.
     */
    public void stopGravityTask() {
        if (gravityTask != null && !gravityTask.isCancelled()) {
            gravityTask.cancel();
            gravityTask = null;
        }
    }

    /**
     * Restores gravity to all entities that were affected by the zero gravity effect.
     */
    public void restoreGravityToEntities() {
        // Restore gravity to tracked entities
        for (Entity entity : affectedEntities) {
            if (entity.isValid() && !entity.isDead()) {
                entity.setGravity(true);
            }
        }
        affectedEntities.clear();
        
        // Also restore gravity to all current entities to be safe
        for (org.bukkit.World world : EventHorizon.getPlugin().getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Player) && entity.isValid() && !entity.isDead()) {
                    entity.setGravity(true);
                }
            }
        }
    }

    /**
     * Executes the zero gravity event by applying modifiers to players and disabling gravity for all entities.
     */
    @Override
    public void execute() {
        super.execute();
        applyZeroGravityToEntities();
        startGravityTask();
    }

    /**
     * Terminates the zero gravity event by removing modifiers from players and restoring gravity to affected entities.
     */
    @Override
    public void terminate() {
        super.terminate();
        stopGravityTask();
        
        // Wait a tick to ensure the task is fully cancelled before restoring gravity
        EventHorizon.getPlugin().getServer().getScheduler().runTaskLater(EventHorizon.getPlugin(), () -> {
            restoreGravityToEntities();
        }, 1L);
    }
}