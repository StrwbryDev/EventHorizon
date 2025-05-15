package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.MarkingUtility;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

/**
 *  Event that spawns a group of nether themed mobs near players.
 */
public class NetherRaid extends BaseMobSpawn {
    /** List of nether themed mob types that can be spawned by this event */
    private static final List<EntityType> NETHER_MOBS = Arrays.asList(
            EntityType.BLAZE,
            EntityType.GHAST,
            EntityType.HOGLIN,
            EntityType.MAGMA_CUBE,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.STRIDER,
            EntityType.WITHER_SKELETON,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.ZOGLIN
    );

    /**
     * Constructs a new NetherRaid event with specific spawn settings.
     * Initializes the event with:
     * - 15 nether themed mobs per spawn
     * - Spawn radius between 5 and 30 blocks horizontally
     * - Vertical spawn range between 5 and 20 blocks
     * - Group spawning enabled with 2-block spacing
     * - Surface spawning allowed on solid ground only
     */
    public NetherRaid() {
        super(NETHER_MOBS, EventClassification.NEGATIVE, "netherRaid");
        setMobCount(15)
                .setMaxSpawnRadius(30)
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
     * Executes the nether raid event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the nether raid event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
