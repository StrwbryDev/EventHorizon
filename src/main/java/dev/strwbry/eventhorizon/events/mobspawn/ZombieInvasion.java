package dev.strwbry.eventhorizon.events.mobspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

/**
 * Event that continuously spawns a zombie near players.
 * This event is classified as a negative event and creates individual zombies
 * that target the nearest player. The zombies spawn continuously at a fixed interval
 * and cannot spawn in water or lava.
 */
public class ZombieInvasion extends BaseMobSpawn {

    /**
     * Constructs a new ZombieInvasion event with specific spawn settings.
     * Initializes the event with:
     * - 1 zombie per spawn
     * - Spawn radius between 3 and 30 blocks horizontally
     * - Vertical spawn range between 3 and 20 blocks
     * - Continuous spawning every 20 ticks
     * - Individual spawning with 2-block spacing
     * - Can spawn in air or on surfaces
     */
    public ZombieInvasion() {
        super(EntityType.ZOMBIE, EventClassification.NEGATIVE, "zombieInvasion");
        setMobCount(AdvConfig.getZombInvMobCt())
                .setMaxSpawnRadius(AdvConfig.getZombInvMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getZombInvMinSpawnRad())
                .setMaxYRadius(AdvConfig.getZombInvMaxYRad())
                .setMinYRadius(AdvConfig.getZombInvMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getZombInvMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getZombInvHeightClearance())
                .setWidthClearance(AdvConfig.getZombInvWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getZombInvSurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getZombInvAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getZombInvAllowLavaSpawn())
                .setUseGroupSpawning(AdvConfig.getZombInvUseGroupSpawn())
                .setGroupSpacing(AdvConfig.getZombInvGroupSpace())
                .setUseContinuousSpawning(AdvConfig.getZombInvUseContinuousSpawn())
                .setSpawnInterval(AdvConfig.getZombInvSpawnInterval());
    }

    /**
     * Handles post-spawn setup for the zombie.
     * Sets the spawned zombie to target the nearest player.
     *
     * @param entity The spawned entity (zombie)
     * @param player The nearest player to target
     */
    @Override
    protected void onMobSpawned(Entity entity, Player player) {
        if (entity instanceof Zombie zombie) {
            zombie.setTarget(player);
        }
    }

    /**
     * Executes the zombie invasion spawn event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the zombie invasion event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
