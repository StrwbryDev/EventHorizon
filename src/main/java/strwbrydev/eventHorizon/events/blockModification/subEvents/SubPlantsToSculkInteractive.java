package strwbrydev.eventHorizon.events.blockModification.subEvents;

import strwbrydev.eventHorizon.EventHorizon;
import strwbrydev.eventHorizon.events.EventClassification;
import strwbrydev.eventHorizon.events.blockModification.BaseBlockModification;
import strwbrydev.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;

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
