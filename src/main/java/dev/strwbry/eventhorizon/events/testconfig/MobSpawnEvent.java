package dev.strwbry.eventhorizon.events.testconfig;

/**
 * Represents a mob spawn event with specific parameters.
 * All MobSpawn Events might not utilize all parameters.
 *
 * @param name The name of the event
 * @param mobCt The number of mobs to spawn
 * @param maxSpawnRadius The maximum radius for mob spawning
 * @param minSpawnRadius The minimum radius for mob spawning
 * @param maxYRadius The maximum Y radius for mob spawning
 * @param minYRadius The minimum Y radius for mob spawning
 * @param maxSpawnAttempts The maximum number of spawn attempts
 * @param spawnInterval The interval between spawns in ticks
 * @param widthClearance The width clearance required for spawning
 * @param heightClearance The height clearance required for spawning
 * @param centerY Whether to center the Y coordinate for spawning
 * @param groupSpacing The spacing between groups of mobs
 * @param surfaceOnlySpawning Whether to spawn mobs only on the surface
 * @param allowWaterSpawning Whether to allow spawning in water
 * @param allowLavaSpawning Whether to allow spawning in lava
 * @param useGroupSpawning Whether to use group spawning
 * @param useContinuousSpawning Whether to use continuous spawning
 * @param useRandMobTypes Whether to use random mob types
 * @implNote This record implements the Event interface.
 * Usage example:
 * <pre>
 * {@code MobSpawnEvent <name> = AdvConfigTest.get<event-name>Event();
 *
 * Integer mobCount = <name>.mobCt();
 * AdvConfigTest.getMobSpawnEvent().mobCt();
 * }
 * </pre>
 * @see Event
 */
public record MobSpawnEvent(
                            String name,
                            Integer mobCt,
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
                            Boolean useRandMobTypes
) implements Event {
    @Override
    public String getName() { return name; }
}
