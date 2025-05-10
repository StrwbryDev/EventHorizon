package dev.strwbry.eventhorizon.events.blockmodification.subevents;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.BaseBlockModification;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;

/**
 * Represents a sub-event that converts plant blocks to interactive sculk blocks.
 * This event is typically triggered as part of the Deep Dark Invasion event,
 * creating interactive sculk features in the affected area.
 */
public class SubPlantsToSculkInteractive extends BaseBlockModification
{
    /**
     * Constructs a new SubPlantsToSculkInteractive event.
     * Initializes the event with negative classification, a cylindrical region of effect,
     * sculk interactive patterns, and plant block masks.
     */
    public SubPlantsToSculkInteractive()
    {
        super(EventClassification.NEGATIVE, "subPlantsToSculkInteractive", new GenericCylindricalRegion(100,400,200),
                EventHorizon.getRandomPatterns().getSculkInteractivePattern(), EventHorizon.getBlockMasks().getPlants(), false);
    }

    /**
     * Executes the plant-to-sculkinteractive conversion using pattern-based replacement.
     */
    public void execute(){
        super.execute(true);

    }

    /**
     * Terminates the event and undoes any modifications.
     */
    @Override
    public void terminate(){
        super.terminate();
    }
}
