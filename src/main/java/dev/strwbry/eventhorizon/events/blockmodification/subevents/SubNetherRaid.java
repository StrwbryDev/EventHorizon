package dev.strwbry.eventhorizon.events.blockmodification.subevents;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.mobspawn.BaseMobSpawn;
import dev.strwbry.eventhorizon.events.utility.MarkingUtility;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a sub-event that spawns Nether-themed hostile mobs during a Nether invasion.
 * This class extends BaseMobSpawn to handle mob spawning mechanics with specific configurations
 * for Nether-themed entities.
 *
 * @see BaseMobSpawn
 * @see EntityType
 */
public class SubNetherRaid extends BaseMobSpawn
{
    /**
     * List of Nether-themed mob types that can be spawned during the raid.
     * Includes various hostile and neutral Nether mobs from Minecraft.
     */
    private static final List<EntityType> NETHER_MOBS = Arrays.asList(
            EntityType.BLAZE,
            EntityType.GHAST,
            EntityType.MAGMA_CUBE,
            EntityType.STRIDER,
            EntityType.WITHER_SKELETON,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.ZOGLIN
    );

    /**
     * Constructs a new SubNetherRaid event with specific spawn configurations.
     * Initializes the raid with Nether mobs and sets various spawning parameters
     * such as mob count, spawn radius, and spawn behavior.
     */
    public SubNetherRaid() {
        super(NETHER_MOBS, EventClassification.NEGATIVE, "subNetherRaid");
        setMobCount(30)
                .setMaxSpawnRadius(100)
                .setMinSpawnRadius(5)
                .setMaxYRadius(20)
                .setMinYRadius(5)
                .setMaxSpawnAttempts(20)
                .setHeightClearance(2)
                .setWidthClearance(1)
                .setSurfaceOnlySpawning(false)
                .setAllowWaterSpawns(false)
                .setAllowLavaSpawns(false)
                .setUseGroupSpawning(false)
                .setGroupSpacing(2)
                .setUseContinuousSpawning(false)
                .setSpawnInterval(60)
                .setRandomMobTypes(true);
    }

    /**
     * Executes the Nether raid event by spawning configured mobs.
     * This implementation uses the parent class's spawn mechanics
     * to create the specified Nether mobs in the world.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the Nether raid event and cleans up spawned entities.
     * First removes all spawned mobs from the world, then calls the
     * parent class's termination method for additional cleanup.
     */
    @Override
    public void terminate() {
        MarkingUtility.deleteAllMarkedEntities(key);
        super.terminate();
    }
}

