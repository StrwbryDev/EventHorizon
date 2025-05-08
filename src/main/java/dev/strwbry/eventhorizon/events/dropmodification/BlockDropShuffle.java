package dev.strwbry.eventhorizon.events.dropmodification;

import dev.strwbry.eventhorizon.events.EventClassification;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Event that implements a block drop shuffling mechanic.
 * When active, all blocks will drop random items from the survival item pool
 * instead of their normal drops. Extends BaseDropModification to handle
 * the drop modification logic.
 */
public class BlockDropShuffle extends BaseDropModification {
    /**
     * Constructs a new BlockDropShuffle event with NEUTRAL classification.
     * Initializes the event with the name "blockDropShuffle".
     */
    public BlockDropShuffle() {
        super(EventClassification.NEUTRAL, "blockDropShuffle");
    }

    /**
     * Sets up the drop modifications by generating a pool of all possible survival items
     * and assigning random drops to every block type except air.
     */
    @Override
    protected void setupDropModifications() {
        List<ItemStack> survivalDropPool = generateSurvivalDropsList();

        for (Material material : Material.values()) {
            if (material.isBlock() && material != Material.AIR) {
                setFixedBlockDrop(material, survivalDropPool);
            }
        }
    }

    /**
     * Executes the block drop shuffle event by setting up modifications
     * and registering event listeners. Delegates to parent class.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the block drop shuffle event by unregistering listeners
     * and clearing modifications. Delegates to parent class.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
