package capstone.team1.eventHorizon.events.blockModification.subEvents;

import capstone.team1.eventHorizon.EventHorizon;
import capstone.team1.eventHorizon.events.EventClassification;
import capstone.team1.eventHorizon.events.blockModification.BaseBlockModification;
import capstone.team1.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;
import org.bukkit.Bukkit;

public class SubAirToSculkInteractive extends BaseBlockModification
{
    public SubAirToSculkInteractive()
    {
        super(EventClassification.NEGATIVE, "subAirToSculkInteractive", new GenericCylindricalRegion(100,400,200),
                EventHorizon.getRandomPatterns().getSculkInteractivePattern(), EventHorizon.getBlockMasks().getAir(), false);
    }


    public void execute(){
        super.execute(true);

    }

    @Override
    public void terminate(){
        super.terminate();
    }
}
