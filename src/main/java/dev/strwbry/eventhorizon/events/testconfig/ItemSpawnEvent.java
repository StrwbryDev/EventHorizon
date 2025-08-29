package dev.strwbry.eventhorizon.events.testconfig;

/**
 * Represents an item spawn event with specific parameters.
 * All ItemSpawn Events might not utilize all parameters.
 *
 * @param name The name of the event
 * @param itemCt The number of items to spawn
 * @param maxSpawnRadius The maximum radius for item spawning
 * @param minSpawnRadius The minimum radius for item spawning
 * @param maxYRadius The maximum Y radius for item spawning
 * @param minYRadius The minimum Y radius for item spawning
 * @param maxSpawnAttempts The maximum number of spawn attempts
 * @param spawnInterval The interval between spawns in ticks
 * @param widthClearance The width clearance required for spawning
 * @param heightClearance The height clearance required for spawning
 * @param centerY Whether to center the Y coordinate for spawning
 * @param groupSpacing The spacing between groups of items
 * @param surfaceOnlySpawning Whether to spawn items only on the surface
 * @param allowWaterSpawning Whether to allow spawning in water
 * @param allowLavaSpawning Whether to allow spawning in lava
 * @param useGroupSpawning Whether to use group spawning
 * @param useContinuousSpawning Whether to use continuous spawning
 * @param useRandItemTypes Whether to use random item types
 * @implNote This record implements the Event interface.
 * Usage example:
 * <pre>
 * {@code ItemSpawnEvent <name> = AdvConfigTest.get<event-name>Event();
 *
 * Integer itemCount = <name>.itemCt();
 * AdvConfigTest.getItemSpawnEvent().itemCt();
 * }
 * </pre>
 * @see Event
 */
public record ItemSpawnEvent(
                            String name,
                            Integer itemCt,
                            Integer maxSpawnRadius,
                            Integer minSpawnRadius,
                            Integer maxYRadius,
                            Integer minYRadius,
                            Integer maxSpawnAttempts,
                            Integer spawnInterval,
                            Integer widthClearance,
                            Integer heightClearance,
                            Boolean centerY,
                            Integer groupSpacing,
                            Boolean surfaceOnlySpawning,
                            Boolean allowWaterSpawning,
                            Boolean allowLavaSpawning,
                            Boolean useGroupSpawning,
                            Boolean useContinuousSpawning,
                            Boolean useRandItemTypes
) implements Event {
    @Override
    public String getName() { return name; }
}
