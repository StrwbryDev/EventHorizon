package dev.strwbry.eventhorizon.events.mobspawn;


import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Event that spawns random mobs near players.
 * This event is classified as neutral and spawns various types of mobs
 * excluding players and ender dragons. Spawns mobs individually at random
 * locations rather than in groups.
 */
public class RandomMobSpawn extends BaseMobSpawn {
    /**
     * Constructs a new RandomMobSpawn event with specific spawn settings.
     * Initializes the event with:
     * - 10 random mobs per spawn
     * - Spawn radius between 3 and 30 blocks horizontally
     * - Vertical spawn range between 3 and 20 blocks
     * - Individual spawning with 2-block spacing
     * - Random mob type selection from all available living entities
     */
    public RandomMobSpawn() {
        super(EntityType.ZOMBIE, EventClassification.NEUTRAL, "randomMobSpawn");
        setMobCount(AdvConfig.getRandMobSpawnMobCt())
                .setMaxSpawnRadius(AdvConfig.getRandMobSpawnMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getRandMobSpawnMinSpawnRad())
                .setMaxYRadius(AdvConfig.getRandMobSpawnMaxYRad())
                .setMinYRadius(AdvConfig.getRandMobSpawnMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getRandMobSpawnMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getRandMobSpawnHeightClearance())
                .setWidthClearance(AdvConfig.getRandMobSpawnWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getRandMobSpawnSurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getRandMobSpawnAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getRandMobSpawnAllowLavaSpawn())
                .setUseGroupSpawning(AdvConfig.getRandMobSpawnUseGroupSpawn())
                .setGroupSpacing(AdvConfig.getRandMobSpawnGroupSpace())

                .setUseContinuousSpawning(AdvConfig.getRandMobSpawnUseContinuousSpawn())
                .setSpawnInterval(AdvConfig.getRandMobSpawnSpawnInterval())
                .setRandomMobTypes(AdvConfig.getRandMobSpawnUseRandMobTypes())
                .setMobTypes(Arrays.stream(EntityType.values())
                    .filter(EntityType::isAlive)
                    .filter(type -> !type.equals(EntityType.PLAYER)
                        && !type.equals(EntityType.ENDER_DRAGON)
                        && !type.equals(EntityType.END_CRYSTAL)
                        && !type.equals(EntityType.ARMOR_STAND))
                    .collect(Collectors.toList()));
    }

    /**
     * Executes the random mob spawn event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the random mob spawn event.
     * Delegates to the parent class implementation.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}