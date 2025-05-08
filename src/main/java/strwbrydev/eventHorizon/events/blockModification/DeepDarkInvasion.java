package strwbrydev.eventHorizon.events.blockModification;

import strwbrydev.eventHorizon.EventHorizon;
import strwbrydev.eventHorizon.events.EventClassification;
import strwbrydev.eventHorizon.events.blockModification.subEvents.SubPlantsToSculkInteractive;
import strwbrydev.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;
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
