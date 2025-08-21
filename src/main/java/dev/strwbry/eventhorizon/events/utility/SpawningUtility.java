package dev.strwbry.eventhorizon.events.utility;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static dev.strwbry.eventhorizon.events.utility.LocationUtility.*;

/**
 * Utility class for spawning entities and items around players in various patterns.
 * Supports both spread and group spawning with configurable parameters.
 */
public class SpawningUtility {
    // Configuration class for spawning behavior
    @FunctionalInterface
    public interface SpawnFunction<T> {
        T spawn(World world, Location location);
    }

    /**
     * Spawns entities or items around a player in a spread pattern.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of entities/items to spawn.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param spawnFunction Function to create the entity/item at the given location.
     * @param <T>           Type of the spawned object (Entity, Item, etc.).
     * @return List of spawned objects.
     */
    public static <T> List<T> spawnSpread(Player player, int spawnCount, SpawnConfig spawnConfig, SpawnFunction<T> spawnFunction) {
        if (player == null || !player.isOnline()) {
            return Collections.emptyList();
        }

        List<T> spawnedResults = new ArrayList<>();
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        int attempts = 0;
        int spawned = 0;

        while (spawned < spawnCount && attempts < spawnConfig.maxSpawnAttempts) {
            attempts++;

            // Calculate random offset
            int xOffset = getRandomOffset(spawnConfig.minSpawnRadius, spawnConfig.maxSpawnRadius);
            int zOffset = getRandomOffset(spawnConfig.minSpawnRadius, spawnConfig.maxSpawnRadius);
            int yOffset = getRandomOffset(spawnConfig.minYRadius, spawnConfig.maxYRadius);

            // Get initial coordinates
            int initialX = playerLocation.getBlockX() + xOffset;
            int initialY = playerLocation.getBlockY() + yOffset;
            int initialZ = playerLocation.getBlockZ() + zOffset;

            // For surface only spawning, find the highest block at this X,Z
            if (spawnConfig.surfaceOnlySpawning) {
                initialY = world.getHighestBlockYAt(initialX, initialZ);
            }

            // Try to find a safe spawning location
            Location spawnLocation = getSafeLocation(player, initialX, initialY, initialZ, spawnConfig);

            if (spawnLocation != null) {
                T spawnedObject = spawnFunction.spawn(world, spawnLocation);
                if (spawnedObject != null) {
                    spawnedResults.add(spawnedObject);
                    spawned++;
                }
            }
        }

        return spawnedResults;
    }

    /**
     * Spawns entities or items in a group around a player.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of entities/items to spawn in the group.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param spawnFunction Function to create the entity/item at the given location.
     * @param <T>           Type of the spawned object (Entity, Item, etc.).
     * @return List of spawned objects.
     */
    public static <T> List<T> spawnGroup(Player player, int spawnCount, SpawnConfig spawnConfig, SpawnFunction<T> spawnFunction) {
        if (player == null || !player.isOnline()) {
            return Collections.emptyList();
        }

        List<T> spawnedResults = new ArrayList<>();
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        // Try to find a suitable location for the group
        Location groupCenter = null;
        int attempts = 0;

        while (groupCenter == null && attempts < spawnConfig.maxSpawnAttempts) {
            attempts++;

            // Calculate random offset
            int xOffset = getRandomOffset(spawnConfig.minSpawnRadius, spawnConfig.maxSpawnRadius);
            int yOffset = getRandomOffset(spawnConfig.minYRadius, spawnConfig.maxYRadius);
            int zOffset = getRandomOffset(spawnConfig.minSpawnRadius, spawnConfig.maxSpawnRadius);

            // Get initial coordinates
            int initialX = playerLocation.getBlockX() + xOffset;
            int initialY = playerLocation.getBlockY() + yOffset;
            int initialZ = playerLocation.getBlockZ() + zOffset;

            // For surface only spawning, find the highest block at this X,Z
            if (spawnConfig.surfaceOnlySpawning) {
                initialY = world.getHighestBlockYAt(initialX, initialZ);
            }

            // Try to find safe spawning location
            groupCenter = getSafeLocation(player, initialX, initialY, initialZ, spawnConfig);
        }

        // If we couldn't find a suitable group center, return empty list
        if (groupCenter == null) {
            return spawnedResults;
        }

        // Spawn the mobs in a group around the center
        int spawned = 0;
        attempts = 0;

        while (spawned < spawnCount && attempts < spawnConfig.maxSpawnAttempts * 2) {
            attempts++;

            // Calculate a close position to the group center
            int xOffset = getRandomOffset(0, spawnConfig.groupSpacing);
            int yOffset = getRandomOffset(0, 1); // TODO: Make this configurable if needed
            int zOffset = getRandomOffset(0, spawnConfig.groupSpacing);

            // Get initial coordinates for group member
            int initialX = groupCenter.getBlockX() + xOffset;
            int initialY = groupCenter.getBlockY() + yOffset;
            int initialZ = groupCenter.getBlockZ() + zOffset;

            // For surface only spawning, adjust Y to the highest block
            if (spawnConfig.surfaceOnlySpawning) {
                initialY = world.getHighestBlockYAt(initialX, initialZ);
            }

            // Try to find a safe location
            Location spawnLocation = getGroupSafeLocation(player, groupCenter, initialX, initialY, initialZ, spawnConfig);

            if (spawnLocation != null) {
                T spawnedObject = spawnFunction.spawn(world, spawnLocation);
                if (spawnedObject != null) {
                    spawnedResults.add(spawnedObject);
                    spawned++;
                }
            }
        }

        return spawnedResults;
    }

    // Convenience methods for entities
    /**
     * Spawns entities in a spread pattern around a player.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of entities to spawn.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param key           Namespaced key for marking the spawned entities.
     * @param entityCreator Function to create the entity at the given location.
     * @return List of spawned entities.
     */
    public static List<Entity> spawnEntitiesSpread(Player player, int spawnCount, SpawnConfig spawnConfig,
                                                   NamespacedKey key, SpawnFunction<Entity> entityCreator) {
        return spawnSpread(player, spawnCount, spawnConfig,
                (world, location) -> {
                    Entity entity = entityCreator.spawn(world, location);
                    if (entity != null && key != null) {
                        MarkingUtility.markEntity(entity, key);
                    }
                    return entity;
                });
    }

    /**
     * Spawns entities in a group around a player.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of entities to spawn in the group.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param key           Namespaced key for marking the spawned entities.
     * @param entityCreator Function to create the entity at the given location.
     * @return List of spawned entities.
     */
    public static List<Entity> spawnEntitiesGroup(Player player, int spawnCount, SpawnConfig spawnConfig,
                                                  NamespacedKey key, SpawnFunction<Entity> entityCreator) {
        return spawnGroup(player, spawnCount, spawnConfig,
                (world, location) -> {
                    Entity entity = entityCreator.spawn(world, location);
                    if (entity != null && key != null) {
                        MarkingUtility.markEntity(entity, key);
                    }
                    return entity;
                });
    }

    // Convenience methods for items
    /**
     * Spawns items in a spread pattern around a player.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of items to spawn.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param key           Namespaced key for marking the spawned items.
     * @param itemSupplier  Supplier for creating the ItemStack for each item.
     * @return List of spawned items.
     */
    public static List<Item> spawnItemsSpread(Player player, int spawnCount, SpawnConfig spawnConfig,
                                              NamespacedKey key, Supplier<ItemStack> itemSupplier) {
        return spawnSpread(player, spawnCount, spawnConfig,
                (world, location) -> {
                    ItemStack itemStack = itemSupplier.get(); // Called for each item
                    Item item = world.dropItemNaturally(location, itemStack);
                    if (key != null) {
                        MarkingUtility.markItem(item, key);
                    }
                    return item;
                });
    }

    /**
     * Spawns items in a group around a player.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of items to spawn in the group.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param key           Namespaced key for marking the spawned items.
     * @param itemSupplier  Supplier for creating the ItemStack for each item.
     * @return List of spawned items.
     */
    public static List<Item> spawnItemsGroup(Player player, int spawnCount, SpawnConfig spawnConfig,
                                             NamespacedKey key, Supplier<ItemStack> itemSupplier) {
        return spawnGroup(player, spawnCount, spawnConfig,
                (world, location) -> {
                    ItemStack itemStack = itemSupplier.get(); // Called for each item
                    Item item = world.dropItemNaturally(location, itemStack);
                    if (key != null) {
                        MarkingUtility.markItem(item, key);
                    }
                    return item;
                });
    }

    // Overloaded convenience methods for spawning single itemStacks
    /**
     * Spawns a single item in a spread pattern around a player.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of items to spawn.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param key           Namespaced key for marking the spawned items.
     * @param itemStack     The ItemStack to spawn.
     * @return List of spawned items.
     */
    public static List<Item> spawnItemsSpread(Player player, int spawnCount, SpawnConfig spawnConfig,
                                              NamespacedKey key, ItemStack itemStack) {
        return spawnItemsSpread(player, spawnCount, spawnConfig, key, () -> itemStack);
    }

    /**
     * Spawns a single item in a group around a player.
     *
     * @param player        The player to spawn around.
     * @param spawnCount    The number of items to spawn in the group.
     * @param spawnConfig   Configuration for spawning behavior.
     * @param key           Namespaced key for marking the spawned items.
     * @param itemStack     The ItemStack to spawn.
     * @return List of spawned items.
     */
    public static List<Item> spawnItemsGroup(Player player, int spawnCount, SpawnConfig spawnConfig,
                                             NamespacedKey key, ItemStack itemStack) {
        return spawnItemsGroup(player, spawnCount, spawnConfig, key, () -> itemStack);
    }
}
