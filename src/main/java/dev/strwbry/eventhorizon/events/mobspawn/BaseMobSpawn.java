package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.BaseEvent;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.LocationUtility.SpawnConfig;
import dev.strwbry.eventhorizon.events.utility.SpawningUtility;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static dev.strwbry.eventhorizon.utility.MsgUtility.log;
import static dev.strwbry.eventhorizon.utility.MsgUtility.warning;

/**
 * Base class for implementing mob spawning events in Minecraft.
 * This class provides comprehensive functionality for spawning mobs around players
 * with various configurable parameters such as spawn radius, mob types, and spawning behavior.
 *
 * The class supports:
 * - Single or continuous spawning
 * - Group or spread spawning
 * - Surface-only or 3D space spawning
 * - Multiple mob types with random selection
 * - Safe location detection for spawning
 *
 * @see BaseEvent
 * @see EventClassification
 */
public abstract class BaseMobSpawn extends BaseEvent {
    /** Plugin instance for server access */
    EventHorizon plugin = EventHorizon.getPlugin();
    /** Random number generator for various random selections */
    protected final Random random = new Random();
    /** Unique identifier for marking spawned items */
    protected final NamespacedKey key;
    /** Spawn configuration for location utilities */
    protected final SpawnConfig spawnConfig = new SpawnConfig();

    // Default configuration values
    /** Default number of mobs to spawn per player */
    private static final int DEFAULT_MOB_COUNT = 5;
    /** Interval between continuous spawns in seconds */
    private static final int DEFAULT_SPAWN_INTERVAL = 60;
    /** Whether mobs spawn in groups or spread out */
    private static final boolean DEFAULT_USE_GROUP_SPAWNING = false;
    /** Whether spawning continues at intervals */
    private static final boolean DEFAULT_USE_CONTINUOUS_SPAWNING = false;
    /** Whether to randomly select from multiple mob types */
    private static final boolean DEFAULT_USE_RANDOM_MOB_TYPES = false;

    // Entity properties
    /** The type of mob to spawn */
    protected EntityType mobType = EntityType.ZOMBIE;
    /** List of possible mob types to spawn */
    protected List<EntityType> mobTypes = new ArrayList<>();
    /** Number of mobs to spawn per player */
    protected int mobCount = DEFAULT_MOB_COUNT;
    /** Number of mobs spawned in last execution */
    private int lastSpawnCount = 0;

    // Flags
    /** Whether mobs spawn in groups or spread out */
    protected boolean useGroupSpawning = DEFAULT_USE_GROUP_SPAWNING;
    /** Whether spawning continues at intervals */
    protected boolean useContinuousSpawning = DEFAULT_USE_CONTINUOUS_SPAWNING;
    /** Whether to randomly select from multiple mob types */
    protected boolean useRandomMobTypes = DEFAULT_USE_RANDOM_MOB_TYPES;

    // Task management
    /** Task for continuous spawning */
    protected BukkitTask continuousTask = null;
    /** Interval between continuous spawns in seconds */
    protected int spawnInterval = DEFAULT_SPAWN_INTERVAL;

    // Constructors
    /**
     * Constructs a mob spawn event with specified classification and name.
     *
     * @param classification The event classification (NEUTRAL, HOSTILE, etc.)
     * @param eventName The unique name for this event
     */
    public BaseMobSpawn(EventClassification classification, String eventName) {
        super(classification, eventName);
        this.key = new NamespacedKey(plugin, this.eventName);
    }
    /**
     * Constructs a mob spawn event with a default mob type and name.
     * The event is classified as NEUTRAL by default.
     *
     * @param defaultMobType The default entity type to spawn
     * @param eventName The unique name for this event
     */
    public BaseMobSpawn(EntityType defaultMobType, String eventName) {
        super(EventClassification.NEUTRAL, eventName);
        this.mobType = defaultMobType;
        this.mobTypes.add(defaultMobType);
        this.key = new NamespacedKey(plugin, this.eventName);
    }
    /**
     * Constructs a mob spawn event with specified mob type, classification, and name.
     *
     * @param defaultMobType The default entity type to spawn
     * @param classification The event classification (NEUTRAL, HOSTILE, etc.)
     * @param eventName The unique name for this event
     */
    public BaseMobSpawn(EntityType defaultMobType, EventClassification classification, String eventName) {
        super(classification, eventName);
        this.mobType = defaultMobType;
        this.mobTypes.add(defaultMobType);
        this.key = new NamespacedKey(plugin, this.eventName);
    }
    /**
     * Constructs a mob spawn event with multiple mob types and specified classification.
     * Enables random mob type selection by default.
     *
     * @param mobTypes List of entity types that can be spawned
     * @param classification The event classification (NEUTRAL, HOSTILE, etc.)
     * @param eventName The unique name for this event
     */
    public BaseMobSpawn(List<EntityType> mobTypes, EventClassification classification, String eventName) {
        super(classification, eventName);
        this.mobTypes.addAll(mobTypes);
        this.useRandomMobTypes = true;
        this.key = new NamespacedKey(plugin, this.eventName);

        // Set default mob type to first in list
        if (!mobTypes.isEmpty()) {
            this.mobType = mobTypes.getFirst();
        } else {
            this.mobTypes.add(EntityType.ZOMBIE);
        }
    }

    /**
     * Spawns mobs for all online players based on the current configuration.
     * If continuous spawning is enabled, starts a repeating task.
     * Otherwise, performs a single spawn cycle.
     */
    @Override
    public void execute() {
        try {
            this.lastSpawnCount = 0;

            if (useContinuousSpawning) {
                // Start continuous task for ongoing spawning
                boolean started = startContinuousTask();
                if (started) {
                    if (useRandomMobTypes) {
                        log("Event " + eventName +
                                " started continuous spawning of random mobs" +
                                " with interval of " + spawnInterval + " seconds");
                    } else {
                        log("Event " + eventName +
                                " started continuous spawning of " + mobType.toString() +
                                " mobs with interval of " + spawnInterval + " seconds");
                    }
                } else {
                    log("Event " + eventName +
                            " tried to start continuous spawning but it was already running");
                }
            } else {
                // Do a one-time spawn for all players
                int spawned = spawnForAllPlayers();
                this.lastSpawnCount = spawned;

                if (useRandomMobTypes) {
                    log("Event " + eventName +
                            " spawned " + spawned + " random mobs across " +
                            plugin.getServer().getOnlinePlayers().size() +
                            " players");
                } else {
                    log("Event " + eventName +
                            " spawned " + spawned + " " + mobType.toString() +
                            " mobs across " + plugin.getServer().getOnlinePlayers().size() +
                            " players");
                }
            }
        } catch (Exception e) {
            warning("Error spawning mobs in " + eventName + ": " + e.getMessage());
        }
    }

    /**
     * Terminates the event by stopping any continuous spawning tasks.
     * Logs appropriate messages based on whether the event was using random mob types
     * and whether the task was successfully stopped.
     */
    @Override
    public void terminate() {
        boolean stopped = stopContinuousTask();

        if (stopped) {
            if (useRandomMobTypes) {
                log("Event " + eventName + " stopped continuous spawning of random mobs");
            } else {
                log("Event " + eventName + " stopped continuous spawning of " + mobType.toString() + " mobs");
            }
        } else {
            warning("Event " + eventName + " tried to stop continuous spawning but it was already stopped");
        }
    }

    /**
     * Starts a continuous task that spawns mobs at regular intervals.
     * The task runs every {@link #spawnInterval} seconds.
     *
     * @return true if the task was successfully started, false if a task is already running
     */
    public boolean startContinuousTask() {
        // Check if task is already running
        if (continuousTask != null && !continuousTask.isCancelled()) {
            return false;
        }

        // Use BukkitRunnable for continuous task that spawns mobs for all players
        continuousTask = new BukkitRunnable() {
            @Override
            public void run() {
                spawnForAllPlayers();
            }
        }.runTaskTimer(plugin, 20L, spawnInterval * 20L);

        return true;
    }

    /**
     * Stops the continuous spawning task if one is running.
     *
     * @return true if a task was stopped, false if no task was running
     */
    public boolean stopContinuousTask() {
        // Check if there's a task to stop
        if (continuousTask == null || continuousTask.isCancelled()) {
            return false;
        }

        // Cancel the task
        continuousTask.cancel();
        continuousTask = null;

        return true;
    }

    /**
     * Hook method called when a mob is spawned.
     * Can be overridden by child classes to implement custom behavior.
     *
     * @param entity The spawned entity
     * @param player The player for whom the mob was spawned
     */
    protected void onMobSpawned(Entity entity, Player player) {
        // Override in child classes for custom behavior
    }

    /**
     * Spawns mobs for all online players.
     * Logs the spawn results for each player and calls {@link #onMobSpawned}
     * for each spawned entity.
     *
     * @return The total number of mobs spawned across all players
     */
    public int spawnForAllPlayers() {
        int totalSpawned = 0;
        List<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());

        for (Player player : players) {
            List<Entity> spawnedEntities = spawnForPlayer(player);
            int playerSpawnCount = spawnedEntities.size();
            totalSpawned += playerSpawnCount;

            if (useRandomMobTypes) {
                log("Spawned " + playerSpawnCount + " random mobs for player " + player.getName());
            } else {
                log("Spawned " + playerSpawnCount + " " + mobType.toString() +
                        " for player " + player.getName());
            }

            // Optional hook for child classes to implement additional logic
            for (Entity entity : spawnedEntities) {
                onMobSpawned(entity, player);
            }
        }
        return totalSpawned;
    }

    /**
     * Spawns mobs for a specific player based on configuration settings.
     * Uses either group or spread spawning depending on {@link #useGroupSpawning}.
     *
     * @param player The player to spawn mobs for
     * @return List of spawned entities, or empty list if player is null or offline
     */
    public List<Entity> spawnForPlayer(Player player) {
        if (player == null || !player.isOnline()) {
            return Collections.emptyList();
        }

        if (useGroupSpawning) {
            return SpawningUtility.spawnEntitiesGroup(player, mobCount, spawnConfig, key,
                    (world, location) -> world.spawnEntity(location, getRandomMobType()));
        } else {
            return SpawningUtility.spawnEntitiesSpread(player, mobCount, spawnConfig, key,
                    (world, location) -> world.spawnEntity(location, getRandomMobType()));
        }
    }

    /**
     * Adds a new mob type to the list of possible spawn types.
     * Does nothing if the type is null or already in the list.
     *
     * @param entityType The entity type to add
     * @return This instance for method chaining
     */
    public BaseMobSpawn addMobType(EntityType entityType) {
        if (entityType != null && !this.mobTypes.contains(entityType)) {
            this.mobTypes.add(entityType);
        }
        return this;
    }

    /**
     * Adds multiple mob types to the list of possible spawn types.
     *
     * @param entityTypes List of entity types to add
     * @return This instance for method chaining
     */
    public BaseMobSpawn addMobTypes(List<EntityType> entityTypes) {
        if (entityTypes != null) {
            for (EntityType type : entityTypes) {
                addMobType(type);
            }
        }
        return this;
    }

    /**
     * Removes a mob type from the list of possible spawn types.
     * If the removed type is the current default, resets to a new default.
     *
     * @param entityType The entity type to remove
     * @return This instance for method chaining
     */
    public BaseMobSpawn removeMobType(EntityType entityType) {
        if (entityType != null && this.mobTypes.remove(entityType)) {
            if (this.mobType.equals(entityType)) {
                resetDefaultMobType();
            }
        }
        return this;
    }

    /**
     * Replaces the current list of mob types with a new list.
     * If the new list is null or empty, keeps the default mob type.
     *
     * @param entityTypes New list of entity types to use
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMobTypes(List<EntityType> entityTypes) {
        this.mobTypes.clear();

        if (entityTypes != null && !entityTypes.isEmpty()) {
            this.mobTypes.addAll(entityTypes);
        }

        resetDefaultMobType();
        return this;
    }

    /**
     * Clears all mob types except the current default type.
     * Ensures at least one mob type remains available for spawning.
     *
     * @return This instance for method chaining
     */
    public BaseMobSpawn clearMobTypes() {
        EntityType currentType = this.mobType;
        this.mobTypes.clear();
        this.mobTypes.add(currentType);
        return this;
    }

    /**
     * Resets the default mob type based on the available mob types list.
     * If the list is not empty, sets the first mob type as default.
     * Otherwise, sets ZOMBIE as the default type and adds it to the list.
     */
    private void resetDefaultMobType() {
        if (!this.mobTypes.isEmpty()) {
            this.mobType = this.mobTypes.getFirst();
        } else {
            this.mobType = EntityType.ZOMBIE;
            this.mobTypes.add(EntityType.ZOMBIE);
        }
    }

    /**
     * Gets a random mob type from the available mob types list.
     * Returns a random type only if random mob types are enabled and the list is not empty.
     * Otherwise, returns the default mob type.
     *
     * @return The selected EntityType to spawn
     */
    protected EntityType getRandomMobType() {
        if (useRandomMobTypes && !mobTypes.isEmpty()) {
            return mobTypes.get(random.nextInt(mobTypes.size()));
        }
        return mobType;
    }

    /**
     * Gets the number of mobs spawned in the last execution.
     *
     * @return The count of mobs spawned in the most recent spawn operation
     */
    public int getLastSpawnCount() {
        return lastSpawnCount;
    }

    /**
     * Gets an unmodifiable list of all possible mob types that can be spawned.
     *
     * @return An unmodifiable List of EntityTypes that can be spawned
     */
    public List<EntityType> getMobTypes() {
        return Collections.unmodifiableList(this.mobTypes);
    }

    /**
     * Sets the default mob type to spawn when random mob types are disabled.
     *
     * @param mobType The EntityType to use as default spawn type
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMobType(EntityType mobType) {
        this.mobType = mobType;
        return this;
    }

    /**
     * Sets the number of mobs to spawn per player.
     *
     * @param count The number of mobs to spawn
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMobCount(int count) {
        this.mobCount = count;
        return this;
    }

    /**
     * Sets the maximum radius from the player where mobs can spawn.
     *
     * @param radius The maximum spawn radius in blocks
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMaxSpawnRadius(int radius) {
        this.spawnConfig.setMaxSpawnRadius(radius);
        return this;
    }

    /**
     * Sets the minimum radius from the player where mobs can spawn.
     *
     * @param radius The minimum spawn radius in blocks
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMinSpawnRadius(int radius) {
        this.spawnConfig.setMinSpawnRadius(radius);
        return this;
    }

    /**
     * Sets the maximum vertical distance from the player where mobs can spawn.
     *
     * @param radius The maximum vertical spawn distance in blocks
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMaxYRadius(int radius) {
        this.spawnConfig.setMaxYRadius(radius);
        return this;
    }

    /**
     * Sets the minimum vertical distance from the player where mobs can spawn.
     *
     * @param radius The minimum vertical spawn distance in blocks
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMinYRadius(int radius) {
        this.spawnConfig.setMinYRadius(radius);
        return this;
    }

    /**
     * Sets the maximum number of attempts to find a valid spawn location.
     *
     * @param attempts Maximum number of spawn location attempts per mob
     * @return This instance for method chaining
     */
    public BaseMobSpawn setMaxSpawnAttempts(int attempts) {
        this.spawnConfig.setMaxSpawnAttempts(attempts);
        return this;
    }

    /**
     * Sets the required horizontal clearance for mob spawning.
     *
     * @param clearance The required horizontal clearance in blocks
     * @return This instance for method chaining
     */
    public BaseMobSpawn setWidthClearance(double clearance) {
        this.spawnConfig.setWidthClearance(clearance);
        return this;
    }

    /**
     * Sets the required vertical clearance for mob spawning.
     *
     * @param clearance The required vertical clearance in blocks
     * @return This instance for method chaining
     */
    public BaseMobSpawn setHeightClearance(double clearance) {
        this.spawnConfig.setHeightClearance(clearance);
        return this;
    }

    /**
     * Sets the spacing between mobs when spawning in groups.
     *
     * @param spacing The distance between mobs in blocks
     * @return This instance for method chaining
     */
    public BaseMobSpawn setGroupSpacing(int spacing) {
        this.spawnConfig.setGroupSpacing(spacing);
        return this;
    }

    /**
     * Sets the interval between continuous spawns.
     *
     * @param seconds The time between spawns in seconds
     * @return This instance for method chaining
     */
    public BaseMobSpawn setSpawnInterval(int seconds) {
        this.spawnInterval = seconds;
        return this;
    }

    /**
     * Sets whether mobs should only spawn on surface blocks.
     *
     * @param surfaceOnly True to restrict spawning to surface blocks only
     * @return This instance for method chaining
     */
    public BaseMobSpawn setSurfaceOnlySpawning(boolean surfaceOnly) {
        this.spawnConfig.setSurfaceOnlySpawning(surfaceOnly);
        return this;
    }

    /**
     * Sets whether mobs can spawn in water.
     *
     * @param allow True to allow spawning in water
     * @return This instance for method chaining
     */
    public BaseMobSpawn setAllowWaterSpawns(boolean allow) {
        this.spawnConfig.setAllowWaterSpawns(allow);
        return this;
    }

    /**
     * Sets whether mobs can spawn in lava.
     *
     * @param allow True to allow spawning in lava
     * @return This instance for method chaining
     */
    public BaseMobSpawn setAllowLavaSpawns(boolean allow) {
        this.spawnConfig.setAllowLavaSpawns(allow);
        return this;
    }

    /**
     * Sets whether mobs should spawn in groups or spread out.
     *
     * @param groupSpawn True to enable group spawning
     * @return This instance for method chaining
     */
    public BaseMobSpawn setUseGroupSpawning(boolean groupSpawn) {
        this.useGroupSpawning = groupSpawn;
        return this;
    }

    /**
     * Sets whether mobs should continuously spawn at intervals.
     *
     * @param continuousSpawn True to enable continuous spawning
     * @return This instance for method chaining
     */
    public BaseMobSpawn setUseContinuousSpawning(boolean continuousSpawn) {
        this.useContinuousSpawning = continuousSpawn;
        return this;
    }

    /**
     * Sets whether to randomly select from available mob types.
     *
     * @param useRandom True to enable random mob type selection
     * @return This instance for method chaining
     */
    public BaseMobSpawn setRandomMobTypes(boolean useRandom) {
        this.useRandomMobTypes = useRandom;
        return this;
    }
}