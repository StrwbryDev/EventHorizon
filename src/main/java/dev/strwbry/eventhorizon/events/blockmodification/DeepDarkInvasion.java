package dev.strwbry.eventhorizon.events.blockmodification;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.subevents.SubPlantsToSculkInteractive;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;
import org.bukkit.Bukkit;

public class DeepDarkInvasion extends BaseBlockModification
{

    public DeepDarkInvasion()
    {
        super(EventClassification.NEGATIVE, "deepDarkInvasion", new GenericCylindricalRegion(100,400,200),
                EventHorizon.getRandomPatterns().getDeepDarkPattern(), EventHorizon.getBlockMasks().getGroundBlocks(), false);
    }

    public void execute(){
        super.execute(true);
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> new SubPlantsToSculkInteractive().execute(true));
    }

    @Override
    public void terminate(){
        super.terminate();
    }
}
