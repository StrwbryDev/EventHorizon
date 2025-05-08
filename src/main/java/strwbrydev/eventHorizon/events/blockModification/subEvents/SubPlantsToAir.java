package strwbrydev.eventHorizon.events.blockModification.subEvents;

import strwbrydev.eventHorizon.EventHorizon;
import strwbrydev.eventHorizon.events.EventClassification;
import strwbrydev.eventHorizon.events.blockModification.BaseBlockModification;
import strwbrydev.eventHorizon.events.utility.fawe.BlockEditor;
import strwbrydev.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;
import org.bukkit.Material;

public class SubPlantsToAir extends BaseBlockModification
{
    public SubPlantsToAir()
    {
        super(EventClassification.NEGATIVE, "subPlantsToAir", new GenericCylindricalRegion(100,400,200), Material.AIR, EventHorizon.getBlockMasks().getPlants(), false);
    }

    public void execute(){
        super.execute(true);
    }

    @Override
    public void terminate(){
        BlockEditor.clearActiveEditSessions();
    }
}
