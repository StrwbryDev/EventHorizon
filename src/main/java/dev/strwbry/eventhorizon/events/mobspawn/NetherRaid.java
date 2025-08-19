package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.units.qual.A;

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
        setMobCount(AdvConfig.getNetherRaidMobCt())
                .setMaxSpawnRadius(AdvConfig.getNetherRaidMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getNetherRaidMinSpawnRad())
                .setMaxYRadius(AdvConfig.getNetherRaidMaxYRad())
                .setMinYRadius(AdvConfig.getNetherRaidMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getNetherRaidMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getNetherRaidHeightClearance())
                .setWidthClearance(AdvConfig.getNetherRaidWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getNetherRaidSurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getNetherRaidAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getNetherRaidAllowLavaSpawn())
                .setUseGroupSpawning(AdvConfig.getNetherRaidUseGroupSpawn())
                .setGroupSpacing(AdvConfig.getNetherRaidGroupSpace())
                .setUseContinuousSpawning(AdvConfig.getNetherRaidUseContinuousSpawn())
                .setSpawnInterval(AdvConfig.getNetherRaidSpawnInterval())
                .setRandomMobTypes(AdvConfig.getNetherRaidUseRandMobTypes());
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
