package dev.strwbry.eventhorizon.events.blockmodification.subevents;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.BaseBlockModification;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;

public class SubPlantsToSculkInteractive extends BaseBlockModification
{
    public SubPlantsToSculkInteractive()
    {
        super(EventClassification.NEGATIVE, "subPlantsToSculkInteractive", new GenericCylindricalRegion(100,400,200),
                EventHorizon.getRandomPatterns().getSculkInteractivePattern(), EventHorizon.getBlockMasks().getPlants(), false);
    }


    public void execute(){
        super.execute(true);

    }

    @Override
    public void terminate(){
        super.terminate();
    }
}
