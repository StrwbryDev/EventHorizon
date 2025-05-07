package capstone.team1.eventHorizon.events.blockModification.subEvents;

import capstone.team1.eventHorizon.EventHorizon;
import capstone.team1.eventHorizon.events.EventClassification;
import capstone.team1.eventHorizon.events.blockModification.BaseBlockModification;
import capstone.team1.eventHorizon.events.utility.fawe.BlockEditor;
import capstone.team1.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;
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
