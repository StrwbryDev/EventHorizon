package dev.strwbry.eventhorizon.events.itemspawn;

import dev.strwbry.eventhorizon.events.EventClassification;
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
    private List<ItemStack> dropItems = new ArrayList<>();

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
        setItemCount(32)
                .setMaxSpawnRadius(20)
                .setMinSpawnRadius(1)
                .setMaxYRadius(10)
                .setMinYRadius(1)
                .setMaxSpawnAttempts(20)
                .setHeightClearance(1)
                .setWidthClearance(1)
                .setSurfaceOnlySpawning(false)
                .setAllowWaterSpawns(false)
                .setAllowLavaSpawns(false)
                .setRandomItemTypes(true);
        dropItems.addAll(generateDropsList());
    }

    /**
     * Generates a list of all possible item stacks.
     * Includes all valid Minecraft items except for AIR.
     *
     * @return List of all valid item stacks
     */
    public List<ItemStack> generateDropsList() {
        List<ItemStack> drops = new ArrayList<>();
        for (Material material : Material.values()) {
            // Check if the material can be represented as an item and is not AIR.
            if (material.isItem() && material != Material.AIR) {
                drops.add(new ItemStack(material));
            }
        }
        return drops;
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