package dev.strwbry.eventhorizon.events.itemspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.ItemUtility;

/**
 * A positive event that spawns various items throughout the world in a "drop party" style.
 * This event generates a predefined number of random items within specified radius parameters.
 * By default, it includes all valid Minecraft items in its potential drop pool.
 */
public class DropParty extends BaseItemSpawn {
    /**
     * Constructs a new DropParty event with predefined configuration settings.
     * Initializes the event with:
     * - 32 items per spawn
     * - Spawn radius between 1-20 blocks
     * - Vertical spawn range between 1-10 blocks
     * - Various item types with weights from 0.5 (rarest) to 5.0 (most common)
     */
    public DropParty() {
        super(ItemUtility.generateWeightedSurvivalDropsList(), EventClassification.POSITIVE, "dropParty");
        setItemCount(32)
                .setMaxSpawnRadius(20)
                .setMinSpawnRadius(1)
                .setMaxYRadius(10)
                .setMinYRadius(1)
                .setMaxSpawnAttempts(64)
                .setHeightClearance(1)
                .setWidthClearance(1)
                .setCenterY(true)
                .setSurfaceOnlySpawning(false)
                .setAllowWaterSpawns(false)
                .setAllowLavaSpawns(false)
                .setRandomItemTypes(true);
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public void terminate() {
        super.terminate();
    }
}