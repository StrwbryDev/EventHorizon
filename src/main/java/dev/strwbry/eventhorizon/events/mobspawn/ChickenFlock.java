package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.entity.EntityType;

/**
 * Event that spawns a group of chickens near players.
 * This event is classified as a positive event and creates a flock of chickens
 * in a group formation. The chickens are spawned on solid ground only and
 * cannot spawn in water or lava.
 */
public class ChickenFlock extends BaseMobSpawn {

    /**
     * Constructs a new ChickenFlock event with specific spawn settings.
     * Initializes the event with:
     * - 5 chickens per spawn
     * - Spawn radius between 3 and 30 blocks horizontally
     * - Vertical spawn range between 3 and 20 blocks
     * - Group spawning enabled with 2-block spacing
     * - Surface spawning allowed on solid ground only
     */
    public ChickenFlock() {
        super(EntityType.CHICKEN, EventClassification.POSITIVE, "chickenFlock");
        setMobCount(AdvConfig.getChickenFMobCt())
                .setMaxSpawnRadius(AdvConfig.getChickenFMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getChickenFMinSpawnRad())
                .setMaxYRadius(AdvConfig.getChickenFMaxYRad())
                .setMinYRadius(AdvConfig.getChickenFMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getChickenFMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getChickenFHeightClearance())
                .setWidthClearance(AdvConfig.getChickenFWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getChickenFSurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getChickenFAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getChickenFAllowLavaSpawn())
                .setUseGroupSpawning(AdvConfig.getChickenFUseGroupSpawn())
                .setGroupSpacing(AdvConfig.getChickenFGroupSpace())
                .setUseContinuousSpawning(AdvConfig.getChickenFUseContinuousSpawn())
                .setSpawnInterval(AdvConfig.getChickenFSpawnInterval());

        /*EventHorizon.getPlugin().getLogger().info(String.format("ChickenFlock event initialized with mob count %d, spawn radius %d-%d, vertical range %d-%d",
                AdvConfig.getChickenFMobCt(),
                AdvConfig.getChickenFMinSpawnRad(),
                AdvConfig.getChickenFMaxSpawnRad(),
                AdvConfig.getChickenFMinYRad(),
                AdvConfig.getChickenFMaxYRad())
        );*/

        EventLoggerUtility.logEventInitialization(this);
    }

    /**
     * Executes the chicken flock spawn event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the chicken flock event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
