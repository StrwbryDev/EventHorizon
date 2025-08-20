package dev.strwbry.eventhorizon.events.utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Utility class for handling location-related operations in Minecraft.
 * Provides methods to find safe spawning locations for players and groups of mobs,
 * taking into account various spawn configurations and world conditions.
 */
public class LocationUtility {
    private static final Random random = new Random();

    /**
     * Attempts to find a safe spawning location for a player based on the initial coordinates and spawn configuration.
     * The location is adjusted based on the spawn configuration and player position.
     *
     * @param player        The player for whom the spawn location is being found.
     * @param initialX      Initial X coordinate for the spawn attempt.
     * @param initialY      Initial Y coordinate for the spawn attempt.
     * @param initialZ      Initial Z coordinate for the spawn attempt.
     * @param config        The spawn configuration containing parameters like radius, clearance, etc.
     * @return A safe Location for spawning, or null if no suitable location was found after max attempts.
     */
    public static Location getSafeLocation(Player player, int initialX, int initialY, int initialZ, SpawnConfig config) {
        World world = player.getWorld();
        int maxTries = config.maxSpawnAttempts * 3;
        int currentTry = 0;

        int x = initialX;
        int y = initialY;
        int z = initialZ;

        // Check world height boundaries
        if (y < world.getMinHeight()) {
            y = world.getMinHeight();
        } else if (y >= world.getMaxHeight()) {
            y = world.getMaxHeight() - 3;
        }

        while (currentTry < maxTries) {
            Location location = new Location(world, x, y, z);

            if (isSafeLocation(location, config)) {
                // Center the location in the block
                location.setX(x + 0.5);
                location.setZ(z + 0.5);
                location.setY(config.centerY ? y + 0.5 : y);
                return location;
            }

            // Adjust position based on issues
            Block block = location.getBlock();
            Block blockBelow = location.clone().subtract(0, 1, 0).getBlock();

            if (block.getType().isSolid()) {
                // If current block is solid, move up
                y++;
            } else if (!blockBelow.getType().isSolid() && !isLiquidLocation(blockBelow, config)) {
                // If there's no solid block below, move down
                y--;
            } else {
                // If other issues, try a small move horizontally
                x += random.nextInt(3) - 1;
                z += random.nextInt(3) - 1;
            }

            // Check if we're still within the radius
            Location playerLocation = player.getLocation();
            double distanceSquared = Math.pow(playerLocation.getX() - x, 2) +
                    Math.pow(playerLocation.getZ() - z, 2);

            if (distanceSquared > Math.pow(config.maxSpawnRadius, 2)) {
                // If we've moved outside the radius, reset to a new random position within radius
                int xOffset = getRandomOffset(config.minSpawnRadius, config.maxSpawnRadius);
                int zOffset = getRandomOffset(config.minSpawnRadius, config.maxSpawnRadius);
                x = playerLocation.getBlockX() + xOffset;
                z = playerLocation.getBlockZ() + zOffset;

                // Reset y based on surface setting
                if (config.surfaceOnlySpawning) {
                    y = world.getHighestBlockYAt(x, z);
                } else {
                    int yOffset = getRandomOffset(config.minYRadius, config.maxYRadius);
                    y = playerLocation.getBlockY() + yOffset;
                }
            }
            currentTry++;
        }
        return null;
    }

    /**
     * Attempts to find a safe spawning location for a group of mobs around a specified center.
     * The location is adjusted based on the spawn configuration and group spacing.
     *
     * @param player        The player for whom the group is being spawned.
     * @param groupCenter   The center location of the group.
     * @param initialX      Initial X coordinate for the spawn attempt.
     * @param initialY      Initial Y coordinate for the spawn attempt.
     * @param initialZ      Initial Z coordinate for the spawn attempt.
     * @param config        The spawn configuration containing parameters like group spacing, clearance, etc.
     * @return A safe Location for spawning, or null if no suitable location was found after max attempts.
     */
    public static Location getGroupSafeLocation(Player player, Location groupCenter, int initialX, int initialY, int initialZ, SpawnConfig config) {
        World world = player.getWorld();
        int maxTries = config.maxSpawnAttempts;
        int currentTry = 0;

        int x = initialX;
        int y = initialY;
        int z = initialZ;

        // Check world height boundaries
        if (y < world.getMinHeight()) {
            y = world.getMinHeight();
        } else if (y >= world.getMaxHeight()) {
            y = world.getMaxHeight() - 3;
        }

        while (currentTry < maxTries) {
            Location location = new Location(world, x, y, z);

            if (isSafeLocation(location, config)) {
                // Center the location in the block
                location.setX(x + 0.5);
                location.setZ(z + 0.5);
                location.setY(config.centerY ? y + 0.5 : y);
                return location;
            }

            // Adjust position based on issues
            Block block = location.getBlock();
            Block blockBelow = location.clone().subtract(0, 1, 0).getBlock();

            if (block.getType().isSolid()) {
                // If current block is solid, move up
                y++;
            } else if (!blockBelow.getType().isSolid() && !isLiquidLocation(blockBelow, config)) {
                // If there's no solid block below, move down
                y--;
            } else {
                // If other issues, try a small move horizontally
                x += random.nextInt(3) - 1;
                z += random.nextInt(3) - 1;
            }

            // Check if we're still within the group spacing radius
            double distanceSquared = Math.pow(groupCenter.getX() - x, 2) + Math.pow(groupCenter.getZ() - z, 2);
            if (distanceSquared > Math.pow(config.groupSpacing, 2)) {
                // If we've moved outside the group radius, reset to a new position closer to group center
                int xOffset = random.nextInt(config.groupSpacing * 2 + 1) - config.groupSpacing;
                int zOffset = random.nextInt(config.groupSpacing * 2 + 1) - config.groupSpacing;
                x = groupCenter.getBlockX() + xOffset;
                z = groupCenter.getBlockZ() + zOffset;

                // Reset y based on surface setting
                if (config.surfaceOnlySpawning) {
                    y = world.getHighestBlockYAt(x, z);
                } else {
                    y = groupCenter.getBlockY() + (random.nextInt(3) - 1); // Small y variance in groups
                }
            }
            currentTry++;
        }

        return null;
    }

    /**
     * Checks if the given location is safe for spawning based on the spawn configuration.
     * A location is considered safe if it is within the world height limits, has a solid block below,
     * and has sufficient clearance around it.
     *
     * @param location The location to check.
     * @param config   The spawn configuration containing clearance settings.
     * @return true if the location is safe, false otherwise.
     */
    private static boolean isSafeLocation(Location location, SpawnConfig config) {
        World world = location.getWorld();
        if (location.getBlockY() < world.getMinHeight() || location.getBlockY() >= world.getMaxHeight()) {
            return false;
        }

        Block block = location.getBlock();
        Block blockBelow = location.clone().subtract(0, 1, 0).getBlock();

        return isSafeBlock(block, config) &&
                blockBelow.getType().isSolid() &&
                checkBlockClearance(location, config);
    }

    /**
     * Checks if the given block is a liquid location (water or lava) based on the spawn configuration.
     *
     * @param block The block to check.
     * @param config The spawn configuration containing liquid spawn settings.
     * @return true if the block is a liquid location allowed by the config, false otherwise.
     */
    private static boolean isLiquidLocation(Block block, SpawnConfig config) {
        Material type = block.getType();
        return (type == Material.WATER && config.allowWaterSpawns) || (type == Material.LAVA && config.allowLavaSpawns);
    }

    /**
     * Checks if the given block is safe for spawning.
     * A block is considered safe if it is air or a liquid location (water/lava) allowed by the config.
     *
     * @param block The block to check.
     * @param config The spawn configuration containing liquid spawn settings.
     * @return true if the block is safe, false otherwise.
     */
    private static boolean isSafeBlock(Block block, SpawnConfig config) {
        return block.getType() == Material.AIR || isLiquidLocation(block, config);
    }

    /**
     * Checks if the area around the given location is clear for spawning.
     * Takes into account width and height clearance specified in the config.
     *
     * @param location The center location to check.
     * @param config   The spawn configuration containing clearance settings.
     * @return true if the area is clear, false otherwise.
     */
    private static boolean checkBlockClearance(Location location, SpawnConfig config) {
        World world = location.getWorld();
        int baseX = location.getBlockX();
        int baseY = location.getBlockY();
        int baseZ = location.getBlockZ();

        int heightBlocks = (int)Math.ceil(config.heightClearance);
        int widthBlocks = (int)Math.ceil(config.widthClearance);

        // Check World height limit
        if (baseY + config.heightClearance >= world.getMaxHeight()) {
            return false;
        }

        for (int y = 0; y < heightBlocks; y++) {
            int checkY = baseY + y;

            int xStart = 0;
            int zStart = 0;

            // If widthClearance is odd, check both negative and positive x and z directions
            if (config.widthClearance % 2 == 1) {
                xStart = -widthBlocks;
                zStart = -widthBlocks;
            }

            for (int x = xStart; x <= widthBlocks; x++) {
                for (int z = zStart; z <= widthBlocks; z++) {
                    if (!isSafeBlock(world.getBlockAt(baseX + x, checkY, baseZ + z), config)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Generates a random offset within the specified range.
     * The offset can be positive or negative.
     *
     * @param min Minimum value of the range (inclusive).
     * @param max Maximum value of the range (inclusive).
     * @return A random offset between -max and max, inclusive.
     */
    public static int getRandomOffset(int min, int max) {
        int range = max - min;
        int offset = random.nextInt(range + 1) + min;
        return random.nextBoolean() ? offset : -offset;
    }

    /**
     * Configuration class for spawning mobs.
     * Allows customization of spawn parameters.
     */
    public static class SpawnConfig {
        public int maxSpawnRadius = 20;
        public int minSpawnRadius = 3;
        public int maxYRadius = 20;
        public int minYRadius = 3;
        public int maxSpawnAttempts = 20;
        public double widthClearance = 1;
        public double heightClearance = 1;
        public int groupSpacing = 3;
        public boolean surfaceOnlySpawning = false;
        public boolean allowWaterSpawns = false;
        public boolean allowLavaSpawns = false;
        public boolean centerY = false;

        public SpawnConfig setMaxSpawnRadius(int radius) {
            this.maxSpawnRadius = radius;
            return this;
        }

        public SpawnConfig setMinSpawnRadius(int radius) {
            this.minSpawnRadius = radius;
            return this;
        }

        public SpawnConfig setMaxYRadius(int radius) {
            this.maxYRadius = radius;
            return this;
        }

        public SpawnConfig setMinYRadius(int radius) {
            this.minYRadius = radius;
            return this;
        }

        public SpawnConfig setMaxSpawnAttempts(int attempts) {
            this.maxSpawnAttempts = attempts;
            return this;
        }

        public SpawnConfig setWidthClearance(double clearance) {
            this.widthClearance = clearance;
            return this;
        }

        public SpawnConfig setHeightClearance(double clearance) {
            this.heightClearance = clearance;
            return this;
        }

        public SpawnConfig setGroupSpacing(int spacing) {
            this.groupSpacing = spacing;
            return this;
        }

        public SpawnConfig setSurfaceOnlySpawning(boolean surfaceOnly) {
            this.surfaceOnlySpawning = surfaceOnly;
            return this;
        }

        public SpawnConfig setAllowWaterSpawns(boolean allow) {
            this.allowWaterSpawns = allow;
            return this;
        }

        public SpawnConfig setAllowLavaSpawns(boolean allow) {
            this.allowLavaSpawns = allow;
            return this;
        }

        public SpawnConfig setCenterY(boolean centerY) {
            this.centerY = centerY;
            return this;
        }
    }
}