package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

/**
 * Event that spawns a pack of angry wolves near players.
 * This event is classified as a negative event and creates a pack of hostile wolves
 * that target the nearest player. The wolves spawn in a group formation and cannot
 * spawn in water or lava.
 */
public class WolfPack extends BaseMobSpawn {
    /**
     * Constructs a new WolfPack event with specific spawn settings.
     * Initializes the event with:
     * - 5 wolves per spawn
     * - Spawn radius between 3 and 30 blocks horizontally
     * - Vertical spawn range between 3 and 20 blocks
     * - Group spawning enabled with 2-block spacing
     * - Surface spawning allowed on solid ground only
     */
    public WolfPack() {
        super(EntityType.WOLF, EventClassification.NEGATIVE, "wolfPack");
        setMobCount(AdvConfig.getWolfPMobCt())
                .setMaxSpawnRadius(AdvConfig.getWolfPMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getWolfPMinSpawnRad())
                .setMaxYRadius(AdvConfig.getWolfPMaxYRad())
                .setMinYRadius(AdvConfig.getWolfPMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getWolfPMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getWolfPHeightClearance())
                .setWidthClearance(AdvConfig.getWolfPWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getWolfPSurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getWolfPAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getWolfPAllowLavaSpawn())
                .setUseGroupSpawning(AdvConfig.getWolfPUseGroupSpawn())
                .setGroupSpacing(AdvConfig.getWolfPGroupSpace())
                .setUseContinuousSpawning(AdvConfig.getWolfPUseContinuousSpawn())
                .setSpawnInterval(AdvConfig.getWolfPSpawnInterval());
    }

    /**
     * Handles post-spawn setup for the wolves.
     * Sets each spawned wolf to angry state and targets the nearest player.
     *
     * @param entity The spawned entity (wolf)
     * @param player The nearest player to target
     */
    @Override
    protected void onMobSpawned(Entity entity, Player player) {
        if (entity instanceof Wolf wolf) {
            wolf.setAngry(true);
            wolf.setTarget(player);
        }
    }

    /**
     * Executes the wolf pack spawn event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the wolf pack event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}