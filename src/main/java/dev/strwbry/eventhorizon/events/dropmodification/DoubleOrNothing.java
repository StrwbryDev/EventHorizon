package dev.strwbry.eventhorizon.events.dropmodification;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * A drop modification event that implements a double-or-nothing mechanic.
 * When active, each block break or entity death has a 50% chance to either:
 * - Double all drops and experience, or
 * - Drop nothing and no experience
 * The outcome is determined randomly but consistently for each block/entity.
 */
public class DoubleOrNothing extends BaseDropModification {
    /** Metadata key used to store the drop outcome for blocks */
    private static final String METADATA_KEY = "doubleOrNothing";

    /**
     * Constructs a new DoubleOrNothing event with NEGATIVE classification.
     * Initializes the event with the name "doubleOrNothing".
     */
    public DoubleOrNothing() {
        super(EventClassification.NEGATIVE, "doubleOrNothing");
    }

    /**
     * No setup required for this modification as it doesn't use predefined drops.
     */
    @Override
    protected void setupDropModifications() {
    }

    /**
     * Handles block breaking events by determining and storing the drop outcome.
     * Uses a consistent random seed based on block location and player name.
     * Sets metadata on the block and modifies XP drops accordingly.
     *
     * @param event the block break event
     */
    @EventHandler(priority = EventPriority.HIGH)
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        super.onBlockBreak(event);

        if (!isActive || event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // Set a random seed based on the block location and player name
        long seed = event.getBlock().getLocation().hashCode() + event.getPlayer().getName().hashCode();
        random.setSeed(seed);

        // Determine fate: true = double, false = nothing
        boolean doubleDrop = random.nextBoolean();

        // Store the outcome as metadata on the block
        event.getBlock().setMetadata(METADATA_KEY, new FixedMetadataValue(EventHorizon.getPlugin(), doubleDrop));

        // Store the original XP to use in BlockDropItemEvent
        int originalXp = event.getExpToDrop();

        if (doubleDrop) {
            // Double the XP
            event.setExpToDrop(originalXp * 2);
        } else {
            // No XP
            event.setExpToDrop(0);
        }
    }

    /**
     * Processes block drops according to the stored outcome.
     * Either doubles all item drops or cancels them entirely.
     *
     * @param event the block drop item event
     * @return true if drops were modified, false if event was ignored
     */
    @Override
    protected boolean handleBlockDrops(BlockDropItemEvent event) {
        if (!isActive || event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getItems().isEmpty()) {
            return false;
        }

        // Retrieve outcome from block metadata
        boolean doubleDrop = false;
        boolean metadataFound = false;

        if (event.getBlock().hasMetadata(METADATA_KEY)) {
            for (MetadataValue value : event.getBlock().getMetadata(METADATA_KEY)) {
                if (value.getOwningPlugin() == EventHorizon.getPlugin()) {
                    doubleDrop = value.asBoolean();
                    event.getBlock().removeMetadata(METADATA_KEY, EventHorizon.getPlugin());
                    metadataFound = true;
                    break;
                }
            }
            event.getBlock().removeMetadata(METADATA_KEY, EventHorizon.getPlugin());
        }

        if (!doubleDrop) {
            // Nothing - cancel all drops
            event.setCancelled(true);
            return true;
        }

        for (Item item : event.getItems()) {
            ItemStack stack = item.getItemStack();
            stack.setAmount(stack.getAmount() * 2);
            item.setItemStack(stack);
        }
        return true;
    }

    /**
     * Processes entity drops with a 50% chance to either double or remove drops.
     * Affects both item drops and experience points.
     *
     * @param event the entity death event
     * @return true if drops were modified, false if event was ignored
     */
    @Override
    protected boolean handleEntityDrops(EntityDeathEvent event) {
        if (!isActive) {
            return false;
        }

        // Get a copy of the original drops and XP
        List<ItemStack> originalDrops = new ArrayList<>();
        for (ItemStack item : event.getDrops()) {
            if (item != null && item.getType() != Material.AIR) {
                originalDrops.add(item.clone());
            }
        }
        int originalXp = event.getDroppedExp();

        // Determine fate: true = double, false = nothing
        boolean doubleDrop = random.nextBoolean();

        if (!doubleDrop) {
            // Nothing - clear all drops and set XP to 0
            event.getDrops().clear();
            event.setDroppedExp(0);
            return true;
        }

        // Double the XP
        event.setDroppedExp(originalXp * 2);

        // If there are no original drops, nothing to modify
        if (originalDrops.isEmpty()) {
            return true;
        }

        // Clear existing drops
        event.getDrops().clear();

        // Add the doubled drops
        for (ItemStack item : originalDrops) {
            ItemStack doubledItem = item.clone();
            doubledItem.setAmount(item.getAmount() * 2);
            event.getDrops().add(doubledItem);
        }

        return true;
    }

    /**
     * Executes the double-or-nothing event by registering listeners.
     * Delegates to parent class.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the double-or-nothing event by unregistering listeners.
     * Delegates to parent class.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}