package dev.strwbry.eventhorizon.events.blockmodification.subevents;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.BaseBlockModification;
import dev.strwbry.eventhorizon.events.utility.fawe.BlockEditor;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;
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
