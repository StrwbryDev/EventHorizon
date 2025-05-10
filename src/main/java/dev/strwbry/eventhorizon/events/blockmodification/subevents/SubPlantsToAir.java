package dev.strwbry.eventhorizon.events.blockmodification.subevents;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.BaseBlockModification;
import dev.strwbry.eventhorizon.events.utility.fawe.BlockEditor;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;
import org.bukkit.Material;

/**
 * A block modification event that replaces plant blocks with air within a cylindrical region.
 * This event is classified as negative as it removes vegetation from the affected area.
 * Extends BaseBlockModification to utilize block replacement functionality.
 */
public class SubPlantsToAir extends BaseBlockModification
{
    /**
     * Constructs a new SubPlantsToAir event.
     * Initializes the event with:
     * - Negative classification
     * - Event name "subPlantsToAir"
     * - Cylindrical region with radius 100 and height 400, centered at y=200
     * - Replacement material set to AIR
     * - Block type mask targeting only plant blocks
     * - Non-inverted mask behavior
     */
    public SubPlantsToAir()
    {
        super(EventClassification.NEGATIVE, "subPlantsToAir", new GenericCylindricalRegion(100,400,200), Material.AIR, EventHorizon.getBlockMasks().getPlants(), false);
    }

    /**
     * Executes the plant to air conversion event.
     * Uses pattern-based replacement mode.
     */
    public void execute(){
        super.execute(true);
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
