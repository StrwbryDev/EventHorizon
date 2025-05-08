package dev.strwbry.eventhorizon.events.blockmodification.subevents;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.BaseBlockModification;
import dev.strwbry.eventhorizon.events.utility.fawe.BlockEditor;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Material;

import java.util.Arrays;

/**
 * A block modification event that replaces water blocks with lava blocks within a cylindrical region.
 * This event is classified as a negative event due to its potentially destructive nature.
 * Extends BaseBlockModification to utilize block replacement functionality.
 */
public class SubWaterToLava extends BaseBlockModification
{
    /**
     * Constructs a new subWaterToLava event.
     * Initializes the event with:
     * - Negative classification
     * - Event name "subWaterToLava"
     * - Cylindrical region with radius 100 and height 400, centered at y=200
     * - Replacement material set to LAVA
     * - Block type mask targeting only water blocks
     * - Non-inverted mask behavior
     */
    public SubWaterToLava()
    {
        super(EventClassification.NEGATIVE, "subWaterToLava", new GenericCylindricalRegion(100,400,200), Material.LAVA, Arrays.asList(BukkitAdapter.asBlockType(Material.WATER)), false);
    }

    /**
     * Executes the water to lava conversion event.
     * Uses single block replacement mode (non-pattern based).
     */
    public void execute(){
        super.execute(false);
    }

    /**
     * Terminates the event and cleans up any active edit sessions.
     * This ensures that no lingering block modifications remain active.
     */
    @Override
    public void terminate(){
        BlockEditor.clearActiveEditSessions();
    }
}
