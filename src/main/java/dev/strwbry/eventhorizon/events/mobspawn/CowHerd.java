package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.entity.EntityType;

/**
 * Event that spawns a group of cows near players.
 * This event is classified as a positive event and creates a herd of cows
 * in a group formation. The cows are spawned on solid ground only and
 * cannot spawn in water or lava.
 */
public class CowHerd extends BaseMobSpawn {

    /**
     * Constructs a new CowHerd event with specific spawn settings.
     * Initializes the event with:
     * - 5 cows per spawn
     * - Spawn radius between 3 and 30 blocks horizontally
     * - Vertical spawn range between 3 and 20 blocks
     * - Group spawning enabled with 2-block spacing
     * - Surface spawning allowed on solid ground only
     */
    public CowHerd() {
        super(EntityType.COW, EventClassification.POSITIVE, "cowHerd");
        setMobCount(AdvConfig.getCowHerdMobCt())
                .setMaxSpawnRadius(AdvConfig.getCowHerdMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getCowHerdMinSpawnRad())
                .setMaxYRadius(AdvConfig.getCowHerdMaxYRad())
                .setMinYRadius(AdvConfig.getCowHerdMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getCowHerdMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getCowHerdHeightClearance())
                .setWidthClearance(AdvConfig.getCowHerdWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getCowHerdSurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getCowHerdAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getCowHerdAllowLavaSpawn())
                .setUseGroupSpawning(AdvConfig.getCowHerdUseGroupSpawn())
                .setGroupSpacing(AdvConfig.getCowHerdGroupSpace())
                .setUseContinuousSpawning(AdvConfig.getCowHerdUseContinuousSpawn())
                .setSpawnInterval(AdvConfig.getCowHerdSpawnInterval());
    }

    /**
     * Executes the cow herd spawn event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the cow herd event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
