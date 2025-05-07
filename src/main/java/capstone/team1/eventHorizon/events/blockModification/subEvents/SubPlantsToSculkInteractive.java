package capstone.team1.eventHorizon.events.blockModification.subEvents;

import capstone.team1.eventHorizon.EventHorizon;
import capstone.team1.eventHorizon.events.EventClassification;
import capstone.team1.eventHorizon.events.blockModification.BaseBlockModification;
import capstone.team1.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;

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
