package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

/**
 *  Event that spawns a group of end themed mobs near players.
 */
public class EndRaid extends BaseMobSpawn {
    /** List of end themed mob types that can be spawned by this event */
    private static final List<EntityType> END_MOBS = Arrays.asList(
            EntityType.ENDERMAN,
            EntityType.ENDERMITE,
            EntityType.SHULKER,
            EntityType.END_CRYSTAL,
            EntityType.PHANTOM
    );

    /**
     * Constructs a new EndRaid event with specific spawn settings.
     * Initializes the event with:
     * - 15 end themed mobs per spawn
     * - Spawn radius between 5 and 30 blocks horizontally
     * - Vertical spawn range between 5 and 20 blocks
     * - Group spawning enabled with 2-block spacing
     * - Surface spawning allowed on solid ground only
     */
    public EndRaid() {
        super(END_MOBS, EventClassification.NEGATIVE, "endRaid");
        setMobCount(AdvConfig.getEndRaidMobCt())
                .setMaxSpawnRadius(AdvConfig.getEndRaidMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getEndRaidMinSpawnRad())
                .setMaxYRadius(AdvConfig.getEndRaidMaxYRad())
                .setMinYRadius(AdvConfig.getEndRaidMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getEndRaidMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getEndRaidHeightClearance())
                .setWidthClearance(AdvConfig.getEndRaidWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getEndRaidSurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getEndRaidAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getEndRaidAllowLavaSpawn())
                .setUseGroupSpawning(AdvConfig.getEndRaidUseGroupSpawn())
                .setGroupSpacing(AdvConfig.getEndRaidGroupSpace())
                .setUseContinuousSpawning(AdvConfig.getEndRaidUseContinuousSpawn())
                .setSpawnInterval(AdvConfig.getEndRaidSpawnInterval())
                .setRandomMobTypes(AdvConfig.getEndRaidUseRandMobTypes());
    }

    /**
     * Executes the end raid event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the end raid event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
