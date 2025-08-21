package dev.strwbry.eventhorizon.events.itemspawn;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.events.utility.ItemUtility;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A positive event that spawns various items throughout the world in a "drop party" style.
 * This event generates a predefined number of random items within specified radius parameters.
 * By default, it includes all valid Minecraft items in its potential drop pool.
 */
public class DropParty extends BaseItemSpawn {
    /** List containing all possible items that can be spawned during this drop party */
    private final List<ItemStack> dropItems = new ArrayList<>();

    /**
     * Constructs a new DropParty event with predefined configuration settings.
     * Initializes the event with:
     * - 32 items per spawn
     * - Spawn radius between 1-20 blocks
     * - Vertical spawn range between 1-10 blocks
     * - Various item types with weights from 0.5 (rarest) to 5.0 (most common)
     */
    public DropParty() {
        super(EventClassification.POSITIVE, "dropParty");
        setItemCount(AdvConfig.getDropPartyItemCt())
                .setMaxSpawnRadius(AdvConfig.getDropPartyMaxSpawnRad())
                .setMinSpawnRadius(AdvConfig.getDropPartyMinSpawnRad())
                .setMaxYRadius(AdvConfig.getDropPartyMaxYRad())
                .setMinYRadius(AdvConfig.getDropPartyMinYRad())
                .setMaxSpawnAttempts(AdvConfig.getDropPartyMaxSpawnAtt())
                .setHeightClearance(AdvConfig.getDropPartyHeightClearance())
                .setWidthClearance(AdvConfig.getDropPartyWidthClearance())
                .setSurfaceOnlySpawning(AdvConfig.getDropPartySurfOnlySpawn())
                .setAllowWaterSpawns(AdvConfig.getDropPartyAllowWaterSpawn())
                .setAllowLavaSpawns(AdvConfig.getDropPartyAllowLavaSpawn())
                .setRandomItemTypes(AdvConfig.getDropPartyUseRandItemTypes());
        dropItems.addAll(ItemUtility.generateSurvivalDropsList());

        /*EventLoggerUtility.logEventInitialization("DropParty",
                "item-count", AdvConfig.getDropPartyItemCt(),
                "max-spawn-radius", AdvConfig.getDropPartyMaxSpawnRad(),
                "min-spawn-radius", AdvConfig.getDropPartyMinSpawnRad(),
                "max-y-radius", AdvConfig.getDropPartyMaxYRad(),
                "min-y-radius", AdvConfig.getDropPartyMinYRad(),
                "max-spawn-attempts", AdvConfig.getDropPartyMaxSpawnAtt(),
                "height-clearance", AdvConfig.getDropPartyHeightClearance(),
                "width-clearance", AdvConfig.getDropPartyWidthClearance(),
                "surface-only-spawning", AdvConfig.getDropPartySurfOnlySpawn(),
                "allow-water-spawns", AdvConfig.getDropPartyAllowWaterSpawn(),
                "allow-lava-spawns", AdvConfig.getDropPartyAllowLavaSpawn(),
                "random-item-types", AdvConfig.getDropPartyUseRandItemTypes()
        );*/

        EventLoggerUtility.logEventInitialization(this);
    }

    /**
     * Selects a random item from the list of possible drops.
     * Falls back to a default item if the list is empty.
     *
     * @return Random item from the list of possible drops
     */
    @Override
    protected ItemStack getRandomWeightedItem() {
        // Return a random item from our list
        if (dropItems.isEmpty()) {
            // Fallback to a default item if the list is somehow empty
            return new ItemStack(Material.STONE);
        }
        return dropItems.get(random.nextInt(dropItems.size()));
    }
}