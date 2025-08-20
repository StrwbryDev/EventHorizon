package dev.strwbry.eventhorizon.events.itemspawn;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.BaseEvent;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.LocationUtility.SpawnConfig;
import dev.strwbry.eventhorizon.events.utility.SpawningUtility;
import dev.strwbry.eventhorizon.utility.MsgUtility;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract base class for item spawning events that handles item distribution around players.
 * This class provides configurable spawning behavior for items in the Minecraft world.
 * Each spawned item is marked with a unique identifier for tracking and later cleanup.
 * The class supports weighted random selection of items and various safety checks
 * to ensure valid spawn locations.
 *
 * @see BaseEvent
 * @see EventClassification
 */
public abstract class BaseItemSpawn extends BaseEvent {
    /** Plugin instance for server access */
    EventHorizon plugin = EventHorizon.getPlugin();
    /** Random number generator for various random selections */
    protected final Random random = new Random();
    /** Unique identifier for marking spawned items */
    protected final NamespacedKey key;
    /** Spawn configuration for location utilities */
    protected final SpawnConfig spawnConfig = new SpawnConfig();

    // Default Configuration Values
    /** Default number of items to spawn per event */
    private static final int DEFAULT_ITEM_COUNT = 5;
    /** Default interval between continuous spawns in seconds */
    private static final int DEFAULT_SPAWN_INTERVAL = 60;
    /** Default setting for group spawning mode */
    private static final boolean DEFAULT_USE_GROUP_SPAWNING = false;
    /** Default setting for continuous spawning mode */
    private static final boolean DEFAULT_USE_CONTINUOUS_SPAWNING = false;
    /** Default setting for random item type selection */
    private static final boolean DEFAULT_USE_RANDOM_ITEM_TYPES = false;

    // Item Properties
    /** The default item type to spawn */
    protected ItemStack itemType = new ItemStack(Material.STONE);
    /** List of items with their spawn weights for random selection */
    protected List<Pair<ItemStack, Double>> weightedItems = new ArrayList<>();
    /** Number of items to spawn per event */
    protected int itemCount = DEFAULT_ITEM_COUNT;
    /** Count of items spawned in the last execution */
    private int lastSpawnCount = 0;

    // Flags
    /** Whether items should spawn in groups */
    protected boolean useGroupSpawning = DEFAULT_USE_GROUP_SPAWNING;
    /** Whether spawning should occur continuously */
    protected boolean useContinuousSpawning = DEFAULT_USE_CONTINUOUS_SPAWNING;
    /** Whether to randomly select item types from weightedItems */
    protected boolean useRandomItemTypes = DEFAULT_USE_RANDOM_ITEM_TYPES;

    // Task Management
    /** Task for continuous spawning mode */
    protected BukkitTask continuousTask = null;
    /** Interval between continuous spawns in seconds */
    protected int spawnInterval = DEFAULT_SPAWN_INTERVAL;

    // Constructors
    /**
     * Constructs a basic item spawn event with default item type.
     *
     * @param classification the event classification (NEUTRAL, HOSTILE, etc.)
     * @param eventName unique identifier for this event
     */
    public BaseItemSpawn(EventClassification classification, String eventName) {
        super(classification, eventName);
        this.key = new NamespacedKey(plugin, this.eventName);
    }
    /**
     * Constructs an item spawn event with a specified default item type.
     *
     * @param defaultItemType the ItemStack to be spawned
     * @param eventName unique identifier for this event
     */
    public BaseItemSpawn(ItemStack defaultItemType, String eventName) {
        super(EventClassification.NEUTRAL, eventName);
        this.itemType = defaultItemType;
        this.key = new NamespacedKey(plugin, this.eventName);
    }
    /**
     * Constructs an item spawn event with specified item type and classification.
     *
     * @param defaultItemType the ItemStack to be spawned
     * @param classification the event classification (NEUTRAL, HOSTILE, etc.)
     * @param eventName unique identifier for this event
     */
    public BaseItemSpawn(ItemStack defaultItemType, EventClassification classification, String eventName) {
        super(classification, eventName);
        this.itemType = defaultItemType;
        this.key = new NamespacedKey(plugin, this.eventName);
    }
    /**
     * Constructs an item spawn event with weighted random item selection.
     *
     * @param weightedItems list of item-weight pairs for random selection
     * @param classification the event classification (NEUTRAL, HOSTILE, etc.)
     * @param eventName unique identifier for this event
     */
    public BaseItemSpawn(List<Pair<ItemStack, Double>> weightedItems, EventClassification classification, String eventName) {
        super(classification, eventName);
        this.weightedItems.addAll(weightedItems);
        this.useRandomItemTypes = true;
        this.key = new NamespacedKey(plugin, this.eventName);

        // Set default item type to first in list
        if (!weightedItems.isEmpty()) {
            this.itemType = weightedItems.getFirst().getLeft();
        } else {
            this.weightedItems.add(Pair.of(new ItemStack(Material.STONE), 1.0));
        }
    }

    /**
     * Executes the item spawn event.
     * Either starts continuous spawning or performs a one-time spawn for all players.
     */
    @Override
    public void execute() {
        try {
            this.lastSpawnCount = 0;

            if (useContinuousSpawning) {
                // Start continuous task for ongoing spawning
                boolean started = startContinuousTask();
                if (started) {
                    if (useRandomItemTypes) {
                        MsgUtility.log("Event " + eventName +
                                " started continuous spawning of random items" +
                                " with interval of " + spawnInterval + " seconds");
                    } else {
                        MsgUtility.log("Event " + eventName +
                                " started continuous spawning of " + itemType.toString() +
                                " items with interval of " + spawnInterval + " seconds");
                    }
                } else {
                    MsgUtility.log("Event " + eventName +
                            " tried to start continuous spawning but it was already running");
                }
            } else {
                // Do a one-time spawn for all players
                int spawned = spawnForAllPlayers();
                this.lastSpawnCount = spawned;

                if (useRandomItemTypes) {
                    MsgUtility.log("Event " + eventName +
                            " spawned " + spawned + " random items across " +
                            plugin.getServer().getOnlinePlayers().size() +
                            " players");
                } else {
                    MsgUtility.log("Event " + eventName +
                            " spawned " + spawned + " " + itemType.toString() +
                            " items across " + plugin.getServer().getOnlinePlayers().size() +
                            " players");
                }
            }
        } catch (Exception e) {
            MsgUtility.warning("Error spawning items in " + eventName + ": " + e.getMessage());
        }
    }

    /**
     * Terminates the event by stopping any continuous spawning tasks.
     */
    @Override
    public void terminate() {
        boolean stopped = stopContinuousTask();

        if (stopped) {
            if (useRandomItemTypes) {
                MsgUtility.log("Event " + eventName + " stopped continuous spawning of random items");
            } else {
                MsgUtility.log("Event " + eventName + " stopped continuous spawning of " + itemType.toString() + " items");
            }
        } else {
            MsgUtility.warning("Event " + eventName + " tried to stop continuous spawning but it was already stopped");
        }
    }

    /**
     * Starts a continuous spawning task if one is not already running.
     *
     * @return true if task was started, false if already running
     */
    public boolean startContinuousTask() {
        // Check if task is already running
        if (continuousTask != null && !continuousTask.isCancelled()) {
            return false;
        }

        // Use BukkitRunnable for continuous task that spawns items for all players
        continuousTask = new BukkitRunnable() {
            @Override
            public void run() {
                spawnForAllPlayers();
            }
        }.runTaskTimer(plugin, 20L, spawnInterval * 20L);

        return true;
    }

    /**
     * Stops the current continuous spawning task if one is running.
     *
     * @return true if task was stopped, false if no task was running
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
     * Hook method called when an item is spawned.
     * Can be overridden by child classes for custom behavior.
     *
     * @param item the spawned item entity
     * @param player the player the item was spawned for
     */
    protected void onItemSpawned(Item item, Player player) {
    }

    /**
     * Spawns items for all online players.
     *
     * @return total number of items spawned across all players
     */
    public int spawnForAllPlayers() {
        int totalSpawned = 0;
        List<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());

        for (Player player : players) {
            List<Item> spawnedItems = spawnForPlayer(player);
            int playerSpawnCount = spawnedItems.size();
            totalSpawned += playerSpawnCount;

            if (useRandomItemTypes) {
                MsgUtility.log("Spawned " + playerSpawnCount + " random items for player " + player.getName());
            } else {
                MsgUtility.log("Spawned " + playerSpawnCount + " " + itemType.toString() +
                        " items for player " + player.getName());
            }

            // Optional hook for child classes to implement additional logic
            for (Item item : spawnedItems) {
                onItemSpawned(item, player);
            }
        }
        return totalSpawned;
    }

    /**
     * Spawns items for a specific player based on the configured settings.
     *
     * @param player the player to spawn items for
     * @return list of spawned Item entities
     */
    public List<Item> spawnForPlayer(Player player) {
        if (player == null || !player.isOnline()) {
            return Collections.emptyList();
        }

        if (useGroupSpawning) {
            return SpawningUtility.spawnItemsGroup(player, itemCount, spawnConfig, key, this::getRandomWeightedItem);
        } else {
            return SpawningUtility.spawnItemsSpread(player, itemCount, spawnConfig, key, this::getRandomWeightedItem);
        }
    }

    /**
     * Adds multiple weighted items to the random selection pool.
     *
     * @param items list of item-weight pairs to add
     * @return this instance for method chaining
     */
    public BaseItemSpawn addWeightedItems(List<Pair<ItemStack, Double>> items) {
        if (items != null) {
            weightedItems.addAll(items);
        }
        return this;
    }

    /**
     * Removes a specific item type from the weighted random selection pool.
     *
     * @param itemToRemove the item type to remove
     * @return this instance for method chaining
     */
    public BaseItemSpawn removeWeightedItem(ItemStack itemToRemove) {
        if (itemToRemove != null) {
            weightedItems.removeIf(pair -> pair.getLeft().isSimilar(itemToRemove));
        }
        return this;
    }

    /**
     * Sets the complete list of weighted items for random selection.
     * Replaces any existing weighted items.
     *
     * @param items list of item-weight pairs
     * @return this instance for method chaining
     */
    public BaseItemSpawn setWeightedItems(List<Pair<ItemStack, Double>> items) {
        // Clear existing items
        weightedItems.clear();

        // Add new ones if not null
        if (items != null) {
            weightedItems.addAll(items);
        }

        return this;
    }

    /**
     * Selects a random item from the weighted item pool.
     *
     * @return randomly selected ItemStack based on weights
     */
    protected ItemStack getRandomWeightedItem() {
        if (!useRandomItemTypes || weightedItems.isEmpty()) {
            return itemType;
        }

        double totalWeight = weightedItems.stream()
                .mapToDouble(Pair::getRight)
                .sum();

        double randomValue = ThreadLocalRandom.current().nextDouble(totalWeight);
        double cumulativeWeight = 0;

        for (Pair<ItemStack, Double> item : weightedItems) {
            cumulativeWeight += item.getRight();
            if (randomValue <= cumulativeWeight) {
                return item.getLeft();
            }
        }

        return weightedItems.getLast().getLeft();
    }

    /**
     * Gets the number of items spawned in the last execution.
     *
     * @return the count of items spawned in the most recent spawn operation
     */
    public int getLastSpawnCount() {
        return lastSpawnCount;
    }

    /**
     * Sets the default item type to spawn when random item types are disabled.
     *
     * @param itemType the ItemStack to use as default spawn type
     * @return this instance for method chaining
     */
    public BaseItemSpawn setItemType(ItemStack itemType) {
        this.itemType = itemType;
        return this;
    }

    /**
     * Sets the number of items to spawn per player.
     *
     * @param count the number of items to spawn
     * @return this instance for method chaining
     */
    public BaseItemSpawn setItemCount(int count) {
        this.itemCount = count;
        return this;
    }

    /**
     * Sets the maximum radius from the player where items can spawn.
     *
     * @param radius the maximum spawn radius in blocks
     * @return this instance for method chaining
     */
    public BaseItemSpawn setMaxSpawnRadius(int radius) {
        this.spawnConfig.setMaxSpawnRadius(radius);
        return this;
    }

    /**
     * Sets the minimum radius from the player where items can spawn.
     *
     * @param radius the minimum spawn radius in blocks
     * @return this instance for method chaining
     */
    public BaseItemSpawn setMinSpawnRadius(int radius) {
        this.spawnConfig.setMinSpawnRadius(radius);
        return this;
    }

    /**
     * Sets the maximum vertical distance from the player where items can spawn.
     *
     * @param radius the maximum vertical spawn distance in blocks
     * @return this instance for method chaining
     */
    public BaseItemSpawn setMaxYRadius(int radius) {
        this.spawnConfig.setMaxYRadius(radius);
        return this;
    }

    /**
     * Sets the minimum vertical distance from the player where items can spawn.
     *
     * @param radius the minimum vertical spawn distance in blocks
     * @return this instance for method chaining
     */
    public BaseItemSpawn setMinYRadius(int radius) {
        this.spawnConfig.setMinYRadius(radius);
        return this;
    }

    /**
     * Sets the maximum number of attempts to find a valid spawn location.
     *
     * @param attempts maximum number of spawn location attempts per item
     * @return this instance for method chaining
     */
    public BaseItemSpawn setMaxSpawnAttempts(int attempts) {
        this.spawnConfig.setMaxSpawnAttempts(attempts);
        return this;
    }

    /**
     * Sets the required horizontal clearance for item spawning.
     *
     * @param clearance the required horizontal clearance in blocks
     * @return this instance for method chaining
     */
    public BaseItemSpawn setWidthClearance(double clearance) {
        this.spawnConfig.setWidthClearance(clearance);
        return this;
    }

    /**
     * Sets the required vertical clearance for item spawning.
     *
     * @param clearance the required vertical clearance in blocks
     * @return this instance for method chaining
     */
    public BaseItemSpawn setHeightClearance(double clearance) {
        this.spawnConfig.setHeightClearance(clearance);
        return this;
    }

    /**
     * Sets the spacing between items when spawning in groups.
     *
     * @param spacing the distance between items in blocks
     * @return this instance for method chaining
     */
    public BaseItemSpawn setGroupSpacing(int spacing) {
        this.spawnConfig.setGroupSpacing(spacing);
        return this;
    }

    /**
     * Sets whether the Y coordinate should be centered when spawning items.
     *
     * @param centerY true to center the Y coordinate, false to use exact spawn height
     * @return this instance for method chaining
     */
    public BaseItemSpawn setCenterY(boolean centerY) {
        this.spawnConfig.setCenterY(centerY);
        return this;
    }

    /**
     * Sets the interval between continuous spawns.
     *
     * @param seconds the time between spawns in seconds
     * @return this instance for method chaining
     */
    public BaseItemSpawn setSpawnInterval(int seconds) {
        this.spawnInterval = seconds;
        return this;
    }

    /**
     * Sets whether items should only spawn on surface blocks.
     *
     * @param surfaceOnly true to restrict spawning to surface blocks only
     * @return this instance for method chaining
     */
    public BaseItemSpawn setSurfaceOnlySpawning(boolean surfaceOnly) {
        this.spawnConfig.setSurfaceOnlySpawning(surfaceOnly);
        return this;
    }

    /**
     * Sets whether items can spawn in water.
     *
     * @param allow true to allow spawning in water
     * @return this instance for method chaining
     */
    public BaseItemSpawn setAllowWaterSpawns(boolean allow) {
        this.spawnConfig.setAllowWaterSpawns(allow);
        return this;
    }

    /**
     * Sets whether items can spawn in lava.
     *
     * @param allow true to allow spawning in lava
     * @return this instance for method chaining
     */
    public BaseItemSpawn setAllowLavaSpawns(boolean allow) {
        this.spawnConfig.setAllowLavaSpawns(allow);
        return this;
    }

    /**
     * Sets whether items should spawn in groups or spread out.
     *
     * @param groupSpawn true to enable group spawning
     * @return this instance for method chaining
     */
    public BaseItemSpawn setUseGroupSpawning(boolean groupSpawn) {
        this.useGroupSpawning = groupSpawn;
        return this;
    }

    /**
     * Sets whether items should continuously spawn at intervals.
     *
     * @param continuousSpawn true to enable continuous spawning
     * @return this instance for method chaining
     */
    public BaseItemSpawn setUseContinuousSpawning(boolean continuousSpawn) {
        this.useContinuousSpawning = continuousSpawn;
        return this;
    }

    /**
     * Sets whether to randomly select from available item types.
     *
     * @param useRandom true to enable random item type selection
     * @return this instance for method chaining
     */
    public BaseItemSpawn setRandomItemTypes(boolean useRandom) {
        this.useRandomItemTypes = useRandom;
        return this;
    }
}
